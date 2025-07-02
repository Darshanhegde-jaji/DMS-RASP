package com.rasp.dms.statergy;



import com.rasp.dms.enums.Operation;
import com.rasp.dms.resource.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface RoleAccessStrategy {

    String getRoleName();

    Set<Operation> getAllowedOperations();

    /**
     * Check whether a user is allowed to perform an operation on a given resource.
     */
    boolean isAllowed(String userId, Operation operation, Optional<String> resourceOwnerId);

//    /**
//     * Return documents this user is allowed to see (e.g., for search/list views).
//     *
//     * @param userId  ID of the requesting user
//     * @param filters Optional search filters (keywords, tags, etc.)
//     * @return list of document DTOs or entities
//     */
//    List<Document> search(String userId, Map<String, Object> filters);
}
