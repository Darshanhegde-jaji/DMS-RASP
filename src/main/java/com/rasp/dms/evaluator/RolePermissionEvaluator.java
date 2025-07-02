package com.rasp.dms.evaluator;


import com.rasp.dms.enums.Operation;
import com.rasp.dms.statergy.RoleAccessStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RolePermissionEvaluator {

    private final Map<String, RoleAccessStrategy> strategyMap;

    @Autowired
    public RolePermissionEvaluator(List<RoleAccessStrategy> strategies) {
        strategyMap = new HashMap<>();
        for (RoleAccessStrategy strategy : strategies) {
            strategyMap.put(strategy.getRoleName(), strategy);
        }
    }

    public boolean hasPermission(String userId, String role, Operation operation, Optional<String> resourceOwnerId) {
        RoleAccessStrategy strategy = strategyMap.get(role);
        return strategy != null && strategy.isAllowed(userId, operation, resourceOwnerId);
    }
    public boolean hasOperationPermission(String role,Operation operation) {
        RoleAccessStrategy strategy = strategyMap.get(role);
        Set<Operation> operations = strategy.getAllowedOperations();
        if(operations.contains(operation)){
            return true;
        }
        return false;
    }
}

