package com.rasp.dms.service;


import com.rasp.dms.dto.DocumentDTO;
import com.rasp.dms.dto.DocumentUploadRequest;
import com.rasp.dms.enums.Operation;
import com.rasp.dms.evaluator.RolePermissionEvaluator;
import com.rasp.dms.helper.DMSRoleAccessHelper;
import com.rasp.dms.helper.DocumentHelper;
import com.rasp.dms.resource.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import platform.resource.BaseResource;
import platform.util.ApplicationException;

import java.util.*;

@Service
public class DocumentService {

    //    private DocumentRepository documentRepository;
    private final BaseResource resource;

    private final RolePermissionEvaluator evaluator;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DMSRoleAccessHelper accessHelper;

    @Autowired
    public DocumentService(RolePermissionEvaluator evaluator, BaseResource repository) {
        this.evaluator = evaluator;
        this.resource = repository;
    }



    public DocumentDTO uploadDocument(MultipartFile file, DocumentUploadRequest request, String username, String role) throws Exception {
        if (!evaluator.hasOperationPermission(role, Operation.CREATE_DOCUMENT)) {
            throw new SecurityException("Access Denied: Not allowed to update this document.");
        }
        Map<String, String> fileInfo = fileStorageService.storeFile(file, request.getAppId(), username);
        String filePath = fileInfo.get("filePath");
        String originalFilename = fileInfo.get("originalFilename");
        byte[] actualFileBytes = file.getBytes();
        long actualFileSize = actualFileBytes.length;

        Document document = new Document();
        document.setName(originalFilename);
        document.setFilePath(filePath);
        document.setFileSize(String.valueOf(actualFileSize));
        document.setContentType(file.getContentType());
        document.setUploadedBy(username);
        document.setAppId(request.getAppId());
        document.setDescription(request.getDescription());

        // Fix: Check for null separately from isEmpty()
        if (request.getTags() == null || request.getTags().isEmpty()) {
            document.setTags(List.of()); // Assuming Document.setTags() accepts List<String>
        } else {
           document.setTags(request.getTags()); // Convert Set to List if needed
        }

        if(document != null) {
            DocumentHelper.getInstance().add(document);
        }

        return convertToDTO(document);
    }

    //    public List<DocumentDTO> getUserDocuments(String userID) {
//        List<Document> documents = documentRepository.findByUploadedBy(userID);
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public List<DocumentDTO> getDocumentsByAppId(String appId) {
//        List<Document> documents = documentRepository.findByAppId(appId);
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public List<DocumentDTO> getDocumentsByRole(String role) {
//        List<Document> documents = documentRepository.findByRole(role);
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public List<DocumentDTO> getUserDocumentsByAppIdAndRole(String username, String appId, String role) {
//        List<Document> documents = documentRepository.findByUploadedByAndAppIdAndRole(username, appId, role);
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
    public BaseResource getDocumentById(String id) {
//        return documentRepository.findById(id).map(this::convertToDTO);
        return DocumentHelper.getInstance().getById(id);
    }
//    public List<DocumentDTO> getAll(String role) throws Exception {
//        if (!accessHelper.hasAccess(role, DMSRoleAccessHelper.Operation.READ_ALL)) {
//            throw new AccessDeniedException("You do not have permission to Read All documents.");
//        }
//        List<Document> documents = documentRepository.findAll();
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }

    public byte[] downloadDocument(String id, String userId, String role) throws Exception {
        BaseResource document = DocumentHelper.getInstance().getById(id);
        Document doc = (Document) document;
        String ownerId = doc.getUploadedBy().toString();
        boolean canAccess =
                evaluator.hasPermission(userId, role, Operation.GET_BY_ID, Optional.of(ownerId)) ||
                        evaluator.hasPermission(userId, role, Operation.GET_ALL, Optional.of(ownerId));
        if (!canAccess) {
            throw new RuntimeException("Access denied");
        }
        return fileStorageService.loadFileUnzipped(doc.getFilePath());
    }


    public boolean deleteDocument(String id, String userID,String role) {
        BaseResource documentOpt = DocumentHelper.getInstance().getById(id);
        Document document = (Document) documentOpt;
        String ownerId = document.getUploadedBy().toString();
        if(!evaluator.hasPermission(userID,role,Operation.DELETE_DOCUMENT, Optional.of(ownerId))){
            throw new RuntimeException("Access denied");
        }
        fileStorageService.deleteFile(document.getFilePath());
//        documentRepository.deleteById(String.valueOf(id));
        DocumentHelper.getInstance().deleteById(id);
        return true;
    }

    public List<DocumentDTO> searchDocuments(String query, String role, String userId) throws Exception {
        BaseResource[] resources = DocumentHelper.getInstance().getAll();
        List<DocumentDTO> documents = new ArrayList<>();

        String lowerQuery = query.toLowerCase();
        boolean hasSearchAllPermission = evaluator.hasOperationPermission(role, Operation.SEARCH_ALL);

        for (BaseResource resource : resources) {
            Document document = (Document) resource;
            DocumentDTO dto = convertToDTO(document);

            boolean matchesQuery =
                    dto.getName().toLowerCase().contains(lowerQuery) ||
                            dto.getDescription().toLowerCase().contains(lowerQuery) ||
                            dto.getTags().stream()
                                    .map(String::toLowerCase)
                                    .anyMatch(tag -> tag.contains(lowerQuery));

            if (matchesQuery) {
                if (hasSearchAllPermission || document.getUploadedBy().toString().equals(userId)) {
                    documents.add(convertToDTO(document));
                }
            }
        }

        return documents;
    }


//    public List<DocumentDTO> searchByTags(List<String> tags){
//        List<Document> documents = documentRepository.findByTagsIn(tags);
//        return documents.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }

//    private DocumentDTO convertToDTO(Document document) {
//        return new DocumentDTO(
//                document.getId(),
//                document.getName(),
//                document.getFilePath(),
//                document.getFileSize(),
//                document.getContentType(),
//                document.getUploadedBy(),
//                document.getAppId(),
//                document.getRole(),
//                document.getDescription(),
//                document.getTags()
//        );
//    }

    public ResponseEntity<?> updateDocument(String id, MultipartFile file, DocumentUploadRequest updateRequest, String userId,String role) throws Exception {
        BaseResource documentOpt = DocumentHelper.getInstance().getById(id);
        Document document = (Document) documentOpt;
//        Document document = documentOpt.get();
        String ownerId = document.getUploadedBy().toString();
        if(!evaluator.hasPermission(userId,role,Operation.UPDATE_DOCUMENT, Optional.of(ownerId))){
            throw new RuntimeException("Access denied");
        }

        fileStorageService.deleteFile(document.getFilePath());

        Map<String, String> fileInfo = fileStorageService.storeFile(file, document.getAppId(), document.getUploadedBy());
        document.setName(fileInfo.get("originalFilename"));
        document.setFilePath(fileInfo.get("filePath"));
        document.setFileSize(String.valueOf(file.getSize()));
        document.setContentType(file.getContentType());
        if (updateRequest.getDescription() != null)
            document.setDescription(updateRequest.getDescription());
        if (updateRequest.getTags() != null)
            document.setTags(updateRequest.getTags());
        DocumentHelper.getInstance().update(document);

        return ResponseEntity.ok(document);
    }

    public DocumentDTO moveDocument(String id, String newPath, String userId, String role) throws Exception {
        // Find the document
        BaseResource documentOpt = DocumentHelper.getInstance().getById(id);
        Document document = (Document) documentOpt;

//        Document document = documentOpt.get();
        String ownerId = document.getUploadedBy().toString();

        // Check permissions using the evaluator
        if (!evaluator.hasPermission(userId, role, Operation.MOVE_DOCUMENT, Optional.of(ownerId))) {
            throw new SecurityException("Access denied: Not allowed to move this document.");
        }


        if (newPath == null || newPath.trim().isEmpty()) {
            throw new IllegalArgumentException("New path cannot be empty");
        }


        String oldPath = document.getFilePath();

        try {

            String newFilePath = fileStorageService.moveFile(oldPath, newPath);


            document.setFilePath(newFilePath);
            DocumentHelper.getInstance().update(document);

            return convertToDTO(document);

        } catch (Exception e) {
            throw new RuntimeException("Failed to move document: " + e.getMessage(), e);
        }
    }

    public DocumentDTO updateDocumentTags(String id, Set<String> tags, String userId, String role) throws ApplicationException {
        BaseResource documentOpt = DocumentHelper.getInstance().getById(id);
        Document document = (Document) documentOpt;
        String ownerId = document.getUploadedBy().toString();
        if(!evaluator.hasPermission(userId,role,Operation.UPDATE_TAGS, Optional.of(ownerId))){
            throw new RuntimeException("Access denied");
        }
        document.setTags(Collections.singletonList(tags.toString()));
        DocumentHelper.getInstance().update(document);
        return convertToDTO(document);
    }

    public List<DocumentDTO> getMyDocuments(String userId) {
        List<DocumentDTO> documents = new ArrayList<>();
        for(BaseResource resource : DocumentHelper.getInstance().getAll()) {
            Document document = (Document) resource;
            if (document.getUploadedBy().toString().equals(userId)) {
                documents.add(convertToDTO(document));
            }
        }
        return documents;
    }

    private DocumentDTO convertToDTO(Document document) {
        List<String> tags;
        if (document.getTags() != null && !document.getTags().isEmpty()) {
            tags = new ArrayList<>(document.getTags()); // Convert to new ArrayList
        } else {
            tags = Collections.emptyList(); // Use emptyList() instead of emptySet()
        }

        return new DocumentDTO(
                document.getId(),
                document.getName(),
                document.getUploadedBy().toString(),
                document.getAppId(),
                Long.parseLong(document.getFileSize()),
                document.getContentType(),
                tags,
                document.getDescription(),
                false
        );
    }
}


