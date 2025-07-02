package com.rasp.dms.helper;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DMSRoleAccessHelper {

    // Supported operations
    public enum Operation {
        READ,
        UPLOAD,
        UPDATE,
        DELETE,
        SEARCH,
        READ_ALL,
        SEARCH_ALL,
    }

    // Supported roles
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_AUDITOR = "auditor";
    public static final String ROLE_USER = "user";

    // Role to operation access mapping
    private static final Map<String, Set<Operation>> roleAccessMap = new HashMap<>();

    static {
        roleAccessMap.put(ROLE_ADMIN, EnumSet.allOf(Operation.class));

        roleAccessMap.put(ROLE_AUDITOR, EnumSet.of(
                Operation.READ,
                Operation.SEARCH_ALL,
                Operation.READ_ALL
        ));

        roleAccessMap.put(ROLE_USER, EnumSet.of(
                Operation.READ,
                Operation.UPLOAD,
                Operation.UPDATE,
                Operation.SEARCH,
                Operation.DELETE
        ));

    }

    /**
     * Checks if the user role has access to the given operation.
     *
     * @param role      user role (e.g., ROLE_ADMIN)
     * @param operation desired operation (UPLOAD, READ, etc.)
     * @return true if access is allowed, false otherwise
     */
    public boolean hasAccess(String role, Operation operation) {
        Set<Operation> allowedOps = roleAccessMap.get(role);
        return allowedOps != null && allowedOps.contains(operation);
    }

    /**
     * Returns the allowed operations for a given role.
     *
     * @param role user role
     * @return set of allowed operations
     */
    public Set<Operation> getAllowedOperations(String role) {
        return roleAccessMap.getOrDefault(role, Collections.emptySet());
    }
    public Set<String> getAllowedRoles(Operation operation) {
        return roleAccessMap.entrySet().stream()
                .filter(entry -> entry.getValue().contains(operation))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}

