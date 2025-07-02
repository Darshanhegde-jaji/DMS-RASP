package com.rasp.dms.statergy;


import com.rasp.dms.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AdminAccessStrategy implements RoleAccessStrategy {


    @Override
    public String getRoleName() {
        return "admin";
    }

    @Override
    public Set<com.rasp.dms.enums.Operation> getAllowedOperations() {
        return EnumSet.allOf(Operation.class);
    }

    @Override
    public boolean isAllowed(String userId, Operation operation, Optional<String> resourceOwnerId) {
        return true;
    }

}
