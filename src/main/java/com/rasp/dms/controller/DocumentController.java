package com.rasp.dms.controller;


import com.rasp.dms.dto.DocumentDTO;
import com.rasp.dms.dto.DocumentUploadRequest;
import com.rasp.dms.resource.Document;
import com.rasp.dms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.resource.BaseResource;
import platform.webservice.BaseService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController  {

    @Autowired
    private DocumentService documentService;


    @PostMapping("/upload")
//    @PreAuthorize("hasRole('DMS')")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("appId") String appId,
            @RequestParam("userid") String userId,
            @RequestParam("dmsRole") String dmsRole,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description) {

        try {
//            String userId = UserContextHolder.getCurrentUserId();
//            String dmsRole = UserContextHolder.getCurrentDmsRole();
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();

            DocumentUploadRequest request = new DocumentUploadRequest();
            request.setAppId(appId);
            request.setRole(dmsRole);
            request.setDescription(description);

            // Parse tags if provided
            if (tags != null && !tags.isEmpty()) {
                request.setTags((List<String>) Set.of(tags.split(",")));
            }

            DocumentDTO document = documentService.uploadDocument(file, request, userId,dmsRole);
            return ResponseEntity.ok(document);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error uploading document: " + e.getMessage());
        }
    }

//    @GetMapping("myDocuments")
//    public ResponseEntity<List<DocumentDTO>> getMyDocuments(@PathVariable String userId) {
//        List<DocumentDTO> documents = documentService.getMyDocuments(userId);
//        return ResponseEntity.ok(documents);
//    }
//
//    @GetMapping("/app/{appId}")
//    public ResponseEntity<List<DocumentDTO>> getDocumentsByAppId(@PathVariable String appId) {
//        List<DocumentDTO> documents = documentService.getDocumentsByAppId(appId);
//        return ResponseEntity.ok(documents);
//    }
//
//    @GetMapping("/role/{role}")
//    public ResponseEntity<List<DocumentDTO>> getDocumentsByRole(@PathVariable String role) {
//        List<DocumentDTO> documents = documentService.getDocumentsByRole(role);
//        return ResponseEntity.ok(documents);
//    }
//
//    @GetMapping("/filter")
//    public ResponseEntity<List<DocumentDTO>> getFilteredDocuments(
//            @RequestParam(required = false) String appId,
//            @RequestParam(required = false) String role) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        List<DocumentDTO> documents;
//
//        if (appId != null && role != null) {
//            documents = documentService.getUserDocumentsByAppIdAndRole(username, appId, role);
//        } else if (appId != null) {
//            documents = documentService.getDocumentsByAppId(appId);
//        } else if (role != null) {
//            documents = documentService.getDocumentsByRole(role);
//        } else {
//            documents = documentService.getUserDocuments(username);
//        }
//
//        return ResponseEntity.ok(documents);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getDocumentById(@PathVariable String id) {
//        Optional<DocumentDTO> document = documentService.getDocumentById(id);
//
//        if (document.isPresent()) {
//            return ResponseEntity.ok(document.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadDocument(@PathVariable String id, @RequestParam("dmsRole") String role, @RequestParam("userId") String userId) {
        try {
            BaseResource documentDTO = documentService.getDocumentById(id);
            Document doc = (Document) documentDTO;
            byte[] fileData = documentService.downloadDocument(id, userId, role);
            ByteArrayResource resource = new ByteArrayResource(fileData);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + doc.getName() + "\"")
                    .contentType(MediaType.parseMediaType(doc.getContentType()))
                    .contentLength(fileData.length) // Use actual size, not database size
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error downloading document: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("userId") String userId,
            @RequestParam("dmsrole") String role) throws Exception {

        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setDescription(description);
        request.setTags((List<String>) tags);

        return documentService.updateDocument(id, file, request, userId,role);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable String id,@RequestParam("dmsRole") String role, @RequestParam("userId") String userId) throws Exception {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
            String username = "rasp";
            boolean deleted = documentService.deleteDocument(id,userId,role);

            if (deleted) {
                return ResponseEntity.ok().body("Document deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error deleting document: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentDTO>> searchDocuments(@RequestParam String query ,@RequestParam("dmsRole") String role, @RequestParam("userId") String userID) throws Exception {
        List<DocumentDTO> documents = documentService.searchDocuments(query,role,userID);
        return ResponseEntity.ok(documents);
    }
    @PutMapping("/{id}/move")
    public ResponseEntity<?> moveDocument(
            @PathVariable String id,
            @RequestParam("newPath") String newPath,
            @RequestParam("userId") String userId,
            @RequestParam("dmsRole") String role) {

        try {
            DocumentDTO movedDocument = documentService.moveDocument(id, newPath, userId, role);
            return ResponseEntity.ok(movedDocument);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error moving document: " + e.getMessage());
        }
    }
    @PutMapping("/{id}/updateTags")
    public ResponseEntity<?> updateDocumentTags(
            @PathVariable String id,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("userId") String userId,
            @RequestParam("dmsRole") String role) {
        try {
            DocumentDTO updatedDocument = documentService.updateDocumentTags(id, tags, userId, role);
            return ResponseEntity.ok(updatedDocument);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error updating tags: " + e.getMessage());
        }
    }
}
