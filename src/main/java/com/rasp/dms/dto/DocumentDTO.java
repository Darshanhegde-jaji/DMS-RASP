package com.rasp.dms.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DocumentDTO {
    private String id;
    private String name;
    private String uploadedBy;
    private String appId;
//    private String role;
    private Long fileSize;
    private String contentType;
    private List<String> tags;
    private String description;
    private Boolean isEncrypted;

    // Constructors
    public DocumentDTO() {
    }

    public DocumentDTO(String id, String name, String uploadedBy, String appId, Long fileSize, String contentType, List<String> tags, String description, Boolean isEncrypted) {
        this.id = id;
        this.name = name;
        this.uploadedBy = uploadedBy;
        this.appId = appId;
//        this.role = role;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.tags = tags;
        this.description = description;
        this.isEncrypted = isEncrypted;
    }



    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }



    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }
}
