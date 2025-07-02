package com.rasp.dms.statergy;


import com.rasp.dms.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuditorAccessStrategy implements RoleAccessStrategy {
//    @Autowired
//    private DocumentRepository documentRepository;

    @Override
    public String getRoleName() {
        return "auditor";
    }

    @Override
    public Set<com.rasp.dms.enums.Operation> getAllowedOperations() {
        return EnumSet.of(Operation.SEARCH, Operation.GET_ALL,Operation.GET_BY_ID,Operation.SEARCH_ALL);
    }

    @Override
    public boolean isAllowed(String userId, Operation operation, Optional<String> resourceOwnerId) {
        return true;
    }

//    @Override
//    public List<Document> search(String userId, Map<String, Object> filters) {
//        return documentRepository.findAll();
//    }
}
