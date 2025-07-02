package com.rasp.dms.statergy;


import com.rasp.dms.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserAccessStrategy implements RoleAccessStrategy {
//    @Autowired
//    private DocumentRepository documentRepository;

    @Override
    public String getRoleName() {
        return "user";
    }

    @Override
    public Set<com.rasp.dms.enums.Operation> getAllowedOperations() {
        return EnumSet.of(Operation.SEARCH, Operation.GET_BY_ID, Operation.CREATE_DOCUMENT, Operation.UPDATE_DOCUMENT,Operation.DELETE_DOCUMENT,Operation.MOVE_DOCUMENT,Operation.UPDATE_TAGS);
    }

    @Override
    public boolean isAllowed(String userId, Operation operation, Optional<String> resourceOwnerId) {
        if (!getAllowedOperations().contains(operation)) return false;

        // If operation is UPDATE or DELETE, ensure user owns the document
        if (operation == Operation.UPDATE_DOCUMENT || operation == Operation.DELETE_DOCUMENT || operation == Operation.GET_BY_ID || operation == Operation.MOVE_DOCUMENT || operation == Operation.UPDATE_TAGS) {
            return resourceOwnerId.isPresent() && userId.equals(resourceOwnerId.get());
        }

        return true;
    }

//    @Override
//    public List<Document> search(String userId, Map<String, Object> filters) {
//        return documentRepository.findByUploadedBy(userId);
//    }

}
