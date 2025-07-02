package com.rasp.dms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Map<String, String> storeFile(MultipartFile file, String appId, String userId) throws IOException {

        String directoryPath = uploadDir + "/" + appId + "/"  + "/" + userId + "/";
        Path directory = Paths.get(directoryPath);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        // Generate unique names
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String baseName = UUID.randomUUID().toString();
        String compressedFilename = baseName + ".zip";
        String zipFilePath = directoryPath + compressedFilename;

        // Compress file into a zip
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            ZipEntry zipEntry = new ZipEntry(originalFilename);
            zipOut.putNextEntry(zipEntry);
            zipOut.write(file.getBytes());
            zipOut.closeEntry();
        }

        Map<String, String> result = new HashMap<>();
        result.put("filePath", zipFilePath);
        result.put("originalFilename", originalFilename);
        result.put("uniqueFilename", compressedFilename);

        return result;
    }

    public byte[] loadFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
    public byte[] loadFileUnzipped(String filePath) throws Exception {
        // Load the zipped file
        byte[] zippedData = loadFile(filePath);

        // Unzip and return the original content
        return unzipFile(zippedData);
    }

    private byte[] unzipFile(byte[] zippedData) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(zippedData);
             ZipInputStream zis = new ZipInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            ZipEntry entry = zis.getNextEntry();
            if (entry != null) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                return baos.toByteArray();
            }
            throw new RuntimeException("No files found in zip");
        }
    }

    public boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
    }

    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    public long getFileSize(String filePath) throws IOException {
        return Files.size(Paths.get(filePath));
    }

    public String getFileName(String filePath) {
        return Paths.get(filePath).getFileName().toString();
    }
    public String moveFile(String currentPath, String newPath) throws Exception {
        try {
            // Validate paths
            if (currentPath == null || newPath == null) {
                throw new IllegalArgumentException("File paths cannot be null");
            }

            File currentFile = new File(currentPath);
            if (!currentFile.exists()) {
                throw new FileNotFoundException("Source file does not exist: " + currentPath);
            }

            // Ensure new path is absolute and create directory structure if needed
            File newFile = new File(newPath);
            File parentDir = newFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create directory structure: " + parentDir.getAbsolutePath());
                }
            }

            // If moving to same location, return current path
            if (currentFile.getAbsolutePath().equals(newFile.getAbsolutePath())) {
                return currentPath;
            }

            // Check if destination already exists
            if (newFile.exists()) {
                throw new IOException("Destination file already exists: " + newPath);
            }

            // Perform the move operation
            boolean moved = currentFile.renameTo(newFile);
            if (!moved) {
                // If rename fails, try copy and delete approach
                try {
                    Files.copy(currentFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    if (!currentFile.delete()) {
                        // Copy succeeded but delete failed - log warning but continue
                        System.err.println("Warning: Original file could not be deleted after copy: " + currentPath);
                    }
                } catch (IOException e) {
                    throw new IOException("Failed to move file from " + currentPath + " to " + newPath, e);
                }
            }

            return newFile.getAbsolutePath();

        } catch (Exception e) {
            throw new Exception("Error moving file: " + e.getMessage(), e);
        }
    }
}
