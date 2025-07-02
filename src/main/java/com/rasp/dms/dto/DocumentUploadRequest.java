package com.rasp.dms.dto;

import java.util.List;
import java.util.Set;

public class DocumentUploadRequest {
    private String appId;
    private String role;
    private List<String> tags;
    private String description;

    // Constructors
    public DocumentUploadRequest() {
    }

    // Getters and Setters
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}