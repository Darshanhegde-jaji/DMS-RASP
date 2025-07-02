/*
 * Copyright 2010-2020 M16, Inc. All rights reserved.
 * This software and documentation contain valuable trade
 * secrets and proprietary property belonging to M16, Inc.
 * None of this software and documentation may be copied,
 * duplicated or disclosed without the express
 * written permission of M16, Inc.
 */

package com.rasp.dms.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import platform.resource.BaseResource;
import platform.util.*;
import org.springframework.stereotype.Component;
import platform.db.*;
import java.util.*;
import com.rasp.dms.message.*;
import com.rasp.dms.helper.*;
import com.rasp.dms.service.*;

/*
 ********** This is a generated class Don't modify it.Extend this file for additional functionality **********
 * 
 */
@Component
 public class Document extends BaseResource {
	private String id = null;
	private String g_created_by_id = null;
	private String g_created_by_name = null;
	private String g_modified_by_id = null;
	private String g_modified_by_name = null;
	private Long g_creation_time = null;
	private Long g_modify_time = null;
	private String g_soft_delete = null;
	private String g_status = null;
	private String archived = null;
	private Long archived_time = null;
	private String name = null;
	private String filePath = null;
	private String uploadedBy = null;
	private String appId = null;
	private List<String> tags = null;
	private String description = null;
	private String isEncrypted = null;
	private String fileSize = null;
	private String contentType = null;
	private Map<String, Object> extra_data = null;

	public static String FIELD_ID = "id";
	public static String FIELD_G_CREATED_BY_ID = "g_created_by_id";
	public static String FIELD_G_CREATED_BY_NAME = "g_created_by_name";
	public static String FIELD_G_MODIFIED_BY_ID = "g_modified_by_id";
	public static String FIELD_G_MODIFIED_BY_NAME = "g_modified_by_name";
	public static String FIELD_G_CREATION_TIME = "g_creation_time";
	public static String FIELD_G_MODIFY_TIME = "g_modify_time";
	public static String FIELD_G_SOFT_DELETE = "g_soft_delete";
	public static String FIELD_G_STATUS = "g_status";
	public static String FIELD_ARCHIVED = "archived";
	public static String FIELD_ARCHIVED_TIME = "archived_time";
	public static String FIELD_NAME = "name";
	public static String FIELD_FILEPATH = "filePath";
	public static String FIELD_UPLOADEDBY = "uploadedBy";
	public static String FIELD_APPID = "appId";
	public static String FIELD_TAGS = "tags";
	public static String FIELD_DESCRIPTION = "description";
	public static String FIELD_ISENCRYPTED = "isEncrypted";
	public static String FIELD_FILESIZE = "fileSize";
	public static String FIELD_CONTENTTYPE = "contentType";
	public static String FIELD_EXTRA_DATA = "extra_data";

	private static final long serialVersionUID = 1L;
	public final static ResourceMetaData metaData = new ResourceMetaData("document");

	static {
		metaData.setCheckBeforeAdd(false);
		metaData.setCheckBeforeUpdate(false);

		metaData.setAllow_duplicate_name(false);
		Field idField = new Field("id", "String");
		idField.setRequired(true);
		metaData.addField(idField);

		Field g_created_by_idField = new Field("g_created_by_id", "String");
		g_created_by_idField.setLength(128);
		metaData.addField(g_created_by_idField);

		Field g_created_by_nameField = new Field("g_created_by_name", "String");
		g_created_by_nameField.setLength(128);
		metaData.addField(g_created_by_nameField);

		Field g_modified_by_idField = new Field("g_modified_by_id", "String");
		g_modified_by_idField.setLength(128);
		metaData.addField(g_modified_by_idField);

		Field g_modified_by_nameField = new Field("g_modified_by_name", "String");
		g_modified_by_nameField.setLength(128);
		metaData.addField(g_modified_by_nameField);

		Field g_creation_timeField = new Field("g_creation_time", "long");
		metaData.addField(g_creation_timeField);

		Field g_modify_timeField = new Field("g_modify_time", "long");
		metaData.addField(g_modify_timeField);

		Field g_soft_deleteField = new Field("g_soft_delete", "String");
		g_soft_deleteField.setDefaultValue("N");
		g_soft_deleteField.setLength(1);
		metaData.addField(g_soft_deleteField);

		Field g_statusField = new Field("g_status", "String");
		g_statusField.setIndexed(true);
		g_statusField.setLength(32);
		metaData.addField(g_statusField);

		Field archivedField = new Field("archived", "String");
		archivedField.setIndexed(true);
		archivedField.setDefaultValue("N");
		archivedField.setLength(1);
		metaData.addField(archivedField);

		Field archived_timeField = new Field("archived_time", "long");
		metaData.addField(archived_timeField);

		Field nameField = new Field("name", "String");
		nameField.setRequired(true);
		metaData.addField(nameField);

		Field filePathField = new Field("filePath", "String");
		filePathField.setRequired(true);
		metaData.addField(filePathField);

		Field uploadedByField = new Field("uploadedBy", "String");
		uploadedByField.setRequired(true);
		metaData.addField(uploadedByField);

		Field appIdField = new Field("appId", "String");
		appIdField.setRequired(true);
		metaData.addField(appIdField);

		Field tagsField = new Field("tags", "String");
		metaData.addField(tagsField);

		Field descriptionField = new Field("description", "String");
		metaData.addField(descriptionField);

		Field isEncryptedField = new Field("isEncrypted", "String");
		isEncryptedField.setRequired(true);
		metaData.addField(isEncryptedField);

		Field fileSizeField = new Field("fileSize", "String");
		fileSizeField.setRequired(true);
		metaData.addField(fileSizeField);

		Field contentTypeField = new Field("contentType", "String");
		contentTypeField.setRequired(true);
		metaData.addField(contentTypeField);

		Field extra_dataField = new Field("extra_data", "Map");
		extra_dataField.setValueType("Object");
		metaData.addField(extra_dataField);


		metaData.setTableName("document");

		metaData.setTag_resource(true);

		metaData.setCluster("documentManagementSystem");
	}

	public Document() {this.setId(Util.getUniqueId());}
	public Document(String id) {this.setId(id);}

	public Document(Document obj) {
		this.id = obj.id;
		this.g_created_by_id = obj.g_created_by_id;
		this.g_created_by_name = obj.g_created_by_name;
		this.g_modified_by_id = obj.g_modified_by_id;
		this.g_modified_by_name = obj.g_modified_by_name;
		this.g_creation_time = obj.g_creation_time;
		this.g_modify_time = obj.g_modify_time;
		this.g_soft_delete = obj.g_soft_delete;
		this.g_status = obj.g_status;
		this.archived = obj.archived;
		this.archived_time = obj.archived_time;
		this.name = obj.name;
		this.filePath = obj.filePath;
		this.uploadedBy = obj.uploadedBy;
		this.appId = obj.appId;
		this.tags = obj.tags;
		this.description = obj.description;
		this.isEncrypted = obj.isEncrypted;
		this.fileSize = obj.fileSize;
		this.contentType = obj.contentType;
		this.extra_data = obj.extra_data;
	}

	public ResourceMetaData getMetaData() {
		return metaData;
	}

	private void setDefaultValues() {
		if(g_soft_delete == null)
			g_soft_delete = "N";
		if(archived == null)
			archived = "N";
	}

	public Map<String, Object> convertResourceToMap(HashMap<String, Object> map) {
		if(id != null)
			map.put("id", id);
		if(g_created_by_id != null)
			map.put("g_created_by_id", g_created_by_id);
		if(g_created_by_name != null)
			map.put("g_created_by_name", g_created_by_name);
		if(g_modified_by_id != null)
			map.put("g_modified_by_id", g_modified_by_id);
		if(g_modified_by_name != null)
			map.put("g_modified_by_name", g_modified_by_name);
		if(g_creation_time != null)
			map.put("g_creation_time", g_creation_time);
		if(g_modify_time != null)
			map.put("g_modify_time", g_modify_time);
		if(g_soft_delete != null)
			map.put("g_soft_delete", g_soft_delete);
		if(g_status != null)
			map.put("g_status", g_status);
		if(archived != null)
			map.put("archived", archived);
		if(archived_time != null)
			map.put("archived_time", archived_time);
		if(name != null)
			map.put("name", name);
		if(filePath != null)
			map.put("filePath", filePath);
		if(uploadedBy != null)
			map.put("uploadedBy", uploadedBy);
		if(appId != null)
			map.put("appId", appId);
		if(tags != null)
			map.put("tags", tags);
		if(description != null)
			map.put("description", description);
		if(isEncrypted != null)
			map.put("isEncrypted", isEncrypted);
		if(fileSize != null)
			map.put("fileSize", fileSize);
		if(contentType != null)
			map.put("contentType", contentType);
		if(extra_data != null)
			map.put("extra_data", extra_data);
		return map;
	}

	public Map<String, Object> validateAndConvertResourceToMap(HashMap<String,Object> map,boolean add) throws ApplicationException {
		if(validateId(add))
			map.put("id", id);
		if(g_created_by_id != null)
			map.put("g_created_by_id", g_created_by_id);
		if(g_created_by_name != null)
			map.put("g_created_by_name", g_created_by_name);
		if(g_modified_by_id != null)
			map.put("g_modified_by_id", g_modified_by_id);
		if(g_modified_by_name != null)
			map.put("g_modified_by_name", g_modified_by_name);
		if(g_creation_time != null)
			map.put("g_creation_time", g_creation_time);
		if(g_modify_time != null)
			map.put("g_modify_time", g_modify_time);
		if(g_soft_delete != null)
			map.put("g_soft_delete", g_soft_delete);
		if(g_status != null)
			map.put("g_status", g_status);
		if(archived != null)
			map.put("archived", archived);
		if(archived_time != null)
			map.put("archived_time", archived_time);
		if(validateName(add))
			map.put("name", name);
		if(validateFilePath(add))
			map.put("filePath", filePath);
		if(validateUploadedBy(add))
			map.put("uploadedBy", uploadedBy);
		if(validateAppId(add))
			map.put("appId", appId);
		if(tags != null)
			map.put("tags", tags);
		if(description != null)
			map.put("description", description);
		if(validateIsEncrypted(add))
			map.put("isEncrypted", isEncrypted);
		if(validateFileSize(add))
			map.put("fileSize", fileSize);
		if(validateContentType(add))
			map.put("contentType", contentType);
		if(extra_data != null)
			map.put("extra_data", extra_data);
		return map;
	}

	public Map<String, Object> convertResourceToPrimaryMap(HashMap<String, Object> map) {
		return map;
	}

	@SuppressWarnings("unchecked")
	public void convertMapToResource(Map<String, Object> map) {
		id = (String) map.get("id");
		g_created_by_id = (String) map.get("g_created_by_id");
		g_created_by_name = (String) map.get("g_created_by_name");
		g_modified_by_id = (String) map.get("g_modified_by_id");
		g_modified_by_name = (String) map.get("g_modified_by_name");
		g_creation_time = (map.get("g_creation_time") == null ? null : ((Number) map.get("g_creation_time")).longValue());
		g_modify_time = (map.get("g_modify_time") == null ? null : ((Number) map.get("g_modify_time")).longValue());
		g_soft_delete = (String) map.get("g_soft_delete");
		g_status = (String) map.get("g_status");
		archived = (String) map.get("archived");
		archived_time = (map.get("archived_time") == null ? null : ((Number) map.get("archived_time")).longValue());
		name = (String) map.get("name");
		filePath = (String) map.get("filePath");
		uploadedBy = (String) map.get("uploadedBy");
		appId = (String) map.get("appId");
		tags = (List<String>) map.get("tags");
		description = (String) map.get("description");
		isEncrypted = (String) map.get("isEncrypted");
		fileSize = (String) map.get("fileSize");
		contentType = (String) map.get("contentType");
		extra_data = (Map<String, Object>) map.get("extra_data");
	}

	@SuppressWarnings("unchecked")
	public void convertTypeUnsafeMapToResource(Map<String, Object> map) {
		Object idObj = map.get("id");
		if(idObj != null)
			id = idObj.toString();

		Object g_created_by_idObj = map.get("g_created_by_id");
		if(g_created_by_idObj != null)
			g_created_by_id = g_created_by_idObj.toString();

		Object g_created_by_nameObj = map.get("g_created_by_name");
		if(g_created_by_nameObj != null)
			g_created_by_name = g_created_by_nameObj.toString();

		Object g_modified_by_idObj = map.get("g_modified_by_id");
		if(g_modified_by_idObj != null)
			g_modified_by_id = g_modified_by_idObj.toString();

		Object g_modified_by_nameObj = map.get("g_modified_by_name");
		if(g_modified_by_nameObj != null)
			g_modified_by_name = g_modified_by_nameObj.toString();

		Object g_creation_timeObj = map.get("g_creation_time");
		if(g_creation_timeObj != null)
			g_creation_time = new Long(g_creation_timeObj.toString());

		Object g_modify_timeObj = map.get("g_modify_time");
		if(g_modify_timeObj != null)
			g_modify_time = new Long(g_modify_timeObj.toString());

		Object g_soft_deleteObj = map.get("g_soft_delete");
		if(g_soft_deleteObj != null)
			g_soft_delete = g_soft_deleteObj.toString();

		Object g_statusObj = map.get("g_status");
		if(g_statusObj != null)
			g_status = g_statusObj.toString();

		Object archivedObj = map.get("archived");
		if(archivedObj != null)
			archived = archivedObj.toString();

		Object archived_timeObj = map.get("archived_time");
		if(archived_timeObj != null)
			archived_time = new Long(archived_timeObj.toString());

		Object nameObj = map.get("name");
		if(nameObj != null)
			name = nameObj.toString();

		Object filePathObj = map.get("filePath");
		if(filePathObj != null)
			filePath = filePathObj.toString();

		Object uploadedByObj = map.get("uploadedBy");
		if(uploadedByObj != null)
			uploadedBy = uploadedByObj.toString();

		Object appIdObj = map.get("appId");
		if(appIdObj != null)
			appId = appIdObj.toString();

		Object tagsObj = map.get("tags");
		if(tagsObj != null)
			tags = Collections.singletonList(tagsObj.toString());

		Object descriptionObj = map.get("description");
		if(descriptionObj != null)
			description = descriptionObj.toString();

		Object isEncryptedObj = map.get("isEncrypted");
		if(isEncryptedObj != null)
			isEncrypted = isEncryptedObj.toString();

		Object fileSizeObj = map.get("fileSize");
		if(fileSizeObj != null)
			fileSize = fileSizeObj.toString();

		Object contentTypeObj = map.get("contentType");
		if(contentTypeObj != null)
			contentType = contentTypeObj.toString();

		extra_data = (Map<String, Object>) map.get("extra_data");
	}

	public void convertPrimaryMapToResource(Map<String, Object> map) {
	}

	public void convertTypeUnsafePrimaryMapToResource(Map<String, Object> map) {
	}

	public String getId() {
		return id;
	}

	public String getIdEx() {
		return id != null ? id : "";
	}

	public void setId(String id) {
		this.id = id;
	}

	public void unSetId() {
		this.id = null;
	}

	public boolean validateId(boolean add) throws ApplicationException {
		if(add && id == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[id]");
		return id != null;
	}

	public String getG_created_by_id() {
		return g_created_by_id;
	}

	public String getG_created_by_idEx() {
		return g_created_by_id != null ? g_created_by_id : "";
	}

	public void setG_created_by_id(String g_created_by_id) {
		this.g_created_by_id = g_created_by_id;
	}

	public void unSetG_created_by_id() {
		this.g_created_by_id = null;
	}

	public String getG_created_by_name() {
		return g_created_by_name;
	}

	public String getG_created_by_nameEx() {
		return g_created_by_name != null ? g_created_by_name : "";
	}

	public void setG_created_by_name(String g_created_by_name) {
		this.g_created_by_name = g_created_by_name;
	}

	public void unSetG_created_by_name() {
		this.g_created_by_name = null;
	}

	public String getG_modified_by_id() {
		return g_modified_by_id;
	}

	public String getG_modified_by_idEx() {
		return g_modified_by_id != null ? g_modified_by_id : "";
	}

	public void setG_modified_by_id(String g_modified_by_id) {
		this.g_modified_by_id = g_modified_by_id;
	}

	public void unSetG_modified_by_id() {
		this.g_modified_by_id = null;
	}

	public String getG_modified_by_name() {
		return g_modified_by_name;
	}

	public String getG_modified_by_nameEx() {
		return g_modified_by_name != null ? g_modified_by_name : "";
	}

	public void setG_modified_by_name(String g_modified_by_name) {
		this.g_modified_by_name = g_modified_by_name;
	}

	public void unSetG_modified_by_name() {
		this.g_modified_by_name = null;
	}

	public Long getG_creation_time() {
		return g_creation_time;
	}

	public long getG_creation_timeEx() {
		return g_creation_time != null ? g_creation_time : 0L;
	}

	public void setG_creation_time(long g_creation_time) {
		this.g_creation_time = g_creation_time;
	}

	@JsonIgnore
	public void setG_creation_time(Long g_creation_time) {
		this.g_creation_time = g_creation_time;
	}

	public void unSetG_creation_time() {
		this.g_creation_time = null;
	}

	public Long getG_modify_time() {
		return g_modify_time;
	}

	public long getG_modify_timeEx() {
		return g_modify_time != null ? g_modify_time : 0L;
	}

	public void setG_modify_time(long g_modify_time) {
		this.g_modify_time = g_modify_time;
	}

	@JsonIgnore
	public void setG_modify_time(Long g_modify_time) {
		this.g_modify_time = g_modify_time;
	}

	public void unSetG_modify_time() {
		this.g_modify_time = null;
	}

	public String getG_soft_delete() {
		return g_soft_delete != null ? g_soft_delete : "N";
	}

	public void setG_soft_delete(String g_soft_delete) {
		this.g_soft_delete = g_soft_delete;
	}

	public void unSetG_soft_delete() {
		this.g_soft_delete = "N";
	}

	public String getG_status() {
		return g_status;
	}

	public String getG_statusEx() {
		return g_status != null ? g_status : "";
	}

	public void setG_status(String g_status) {
		this.g_status = g_status;
	}

	public void unSetG_status() {
		this.g_status = null;
	}

	public String getArchived() {
		return archived != null ? archived : "N";
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}

	public void unSetArchived() {
		this.archived = "N";
	}

	public Long getArchived_time() {
		return archived_time;
	}

	public long getArchived_timeEx() {
		return archived_time != null ? archived_time : 0L;
	}

	public void setArchived_time(long archived_time) {
		this.archived_time = archived_time;
	}

	@JsonIgnore
	public void setArchived_time(Long archived_time) {
		this.archived_time = archived_time;
	}

	public void unSetArchived_time() {
		this.archived_time = null;
	}

	public String getName() {
		return name;
	}

	public String getNameEx() {
		return name != null ? name : "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void unSetName() {
		this.name = null;
	}

	public boolean validateName(boolean add) throws ApplicationException {
		if(add && name == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[name]");
		return name != null;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFilePathEx() {
		return filePath != null ? filePath : "";
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void unSetFilePath() {
		this.filePath = null;
	}

	public boolean validateFilePath(boolean add) throws ApplicationException {
		if(add && filePath == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[filePath]");
		return filePath != null;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public String getUploadedByEx() {
		return uploadedBy != null ? uploadedBy : "";
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public void unSetUploadedBy() {
		this.uploadedBy = null;
	}

	public boolean validateUploadedBy(boolean add) throws ApplicationException {
		if(add && uploadedBy == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[uploadedBy]");
		return uploadedBy != null;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppIdEx() {
		return appId != null ? appId : "";
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void unSetAppId() {
		this.appId = null;
	}

	public boolean validateAppId(boolean add) throws ApplicationException {
		if(add && appId == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[appId]");
		return appId != null;
	}

	public List<String> getTags() {
		return tags;
	}

//	public String getTagsEx() {
//		return tags != null ? tags : "";
//	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void unSetTags() {
		this.tags = null;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionEx() {
		return description != null ? description : "";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void unSetDescription() {
		this.description = null;
	}

	public String getIsEncrypted() {
		return isEncrypted;
	}

	public String getIsEncryptedEx() {
		return isEncrypted != null ? isEncrypted : "";
	}

	public void setIsEncrypted(String isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	public void unSetIsEncrypted() {
		this.isEncrypted = null;
	}

	public boolean validateIsEncrypted(boolean add) throws ApplicationException {
		if(add && isEncrypted == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[isEncrypted]");
		return isEncrypted != null;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getFileSizeEx() {
		return fileSize != null ? fileSize : "";
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void unSetFileSize() {
		this.fileSize = null;
	}

	public boolean validateFileSize(boolean add) throws ApplicationException {
		if(add && fileSize == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[fileSize]");
		return fileSize != null;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContentTypeEx() {
		return contentType != null ? contentType : "";
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void unSetContentType() {
		this.contentType = null;
	}

	public boolean validateContentType(boolean add) throws ApplicationException {
		if(add && contentType == null)
			throw new ApplicationException(ExceptionSeverity.ERROR, "Requierd validation Failed[contentType]");
		return contentType != null;
	}

	public Map<String, Object> getExtra_data() {
		return extra_data;
	}

	public Object getExtra_data(String key) {
		return extra_data == null ? null : extra_data.get(key);
	}

	public void setExtra_data(Map<String, Object> extra_data) {
		this.extra_data = extra_data;
	}

	public void setExtra_data(String key, Object value) {
		if(extra_data == null)
			extra_data = new HashMap<String, Object>();
		extra_data.put(key, value);
	}

	public void unSetExtra_data() {
		this.extra_data = null;
	}
	public String getCluster() {
		return "documentManagementSystem";
	}
	public String getClusterType() {
		return "REPLICATED";
	}
	public  Class<?> getResultClass() {return DocumentResult.class;};
	public  Class<?> getMessageClass() {return DocumentMessage.class;};
	public  Class<?> getHelperClass() {return DocumentHelper.class;};
	public  Class<?> getServiceClass() {return DocumentService.class;};
}