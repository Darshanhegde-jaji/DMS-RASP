package com.rasp.dms.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenUtil {

    @Value("${keycloak.token.introspect.url:http://localhost:4000/realms/myRealm/protocol/openid-connect/token/introspect}")
    private String introspectUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public JwtTokenUtil() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public boolean validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(clientId, clientSecret);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", token);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(introspectUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                return jsonResponse.get("active").asBoolean();
            }

            return false;
        } catch (Exception e) {
            System.err.println("Error validating token with Keycloak: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(clientId, clientSecret);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", token);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(introspectUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                if (jsonResponse.get("active").asBoolean()) {
                    return jsonResponse.get("username").asText();
                }
            }

            return null;
        } catch (Exception e) {
            System.err.println("Error getting username from token: " + e.getMessage());
            return null;
        }
    }

    public TokenIntrospectionResponse introspectToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(clientId, clientSecret);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", token);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(introspectUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());

                // Extract roles from resource_access or realm_access
                List<String> roles = extractRoles(jsonResponse);

                return TokenIntrospectionResponse.builder()
                        .active(jsonResponse.get("active").asBoolean())
                        .username(jsonResponse.has("username") ? jsonResponse.get("username").asText() : null)
                        .clientId(jsonResponse.has("client_id") ? jsonResponse.get("client_id").asText() : null)
                        .scope(jsonResponse.has("scope") ? jsonResponse.get("scope").asText() : null)
                        .exp(jsonResponse.has("exp") ? jsonResponse.get("exp").asLong() : null)
                        .iat(jsonResponse.has("iat") ? jsonResponse.get("iat").asLong() : null)
                        .sub(jsonResponse.has("sub") ? jsonResponse.get("sub").asText() : null)
                        .userId(jsonResponse.has("sub") ? jsonResponse.get("sub").asText() : null) // Using sub as user ID
                        .roles(roles)
                        .build();
            }

            return TokenIntrospectionResponse.builder().active(false).build();
        } catch (Exception e) {
            System.err.println("Error introspecting token: " + e.getMessage());
            return TokenIntrospectionResponse.builder().active(false).build();
        }
    }

    private List<String> extractRoles(JsonNode jsonResponse) {
        List<String> roles = new ArrayList<>();

        // Check resource_access for client-specific roles
        if (jsonResponse.has("resource_access") && jsonResponse.get("resource_access").has(clientId)) {
            JsonNode clientRoles = jsonResponse.get("resource_access").get(clientId);
            if (clientRoles.has("roles")) {
                clientRoles.get("roles").forEach(role -> roles.add(role.asText()));
            }
        }

        // Check realm_access for realm roles
        if (jsonResponse.has("realm_access") && jsonResponse.get("realm_access").has("roles")) {
            jsonResponse.get("realm_access").get("roles").forEach(role -> roles.add(role.asText()));
        }

        return roles;
    }

    // Inner class for token introspection response
    public static class TokenIntrospectionResponse {
        private boolean active;
        private String username;
        private String clientId;
        private String scope;
        private Long exp;
        private Long iat;
        private String sub;
        private String userId;
        private List<String> roles;

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private boolean active;
            private String username;
            private String clientId;
            private String scope;
            private Long exp;
            private Long iat;
            private String sub;
            private String userId;
            private List<String> roles;

            public Builder active(boolean active) {
                this.active = active;
                return this;
            }

            public Builder username(String username) {
                this.username = username;
                return this;
            }

            public Builder clientId(String clientId) {
                this.clientId = clientId;
                return this;
            }

            public Builder scope(String scope) {
                this.scope = scope;
                return this;
            }

            public Builder exp(Long exp) {
                this.exp = exp;
                return this;
            }

            public Builder iat(Long iat) {
                this.iat = iat;
                return this;
            }

            public Builder sub(String sub) {
                this.sub = sub;
                return this;
            }

            public Builder userId(String userId) {
                this.userId = userId;
                return this;
            }

            public Builder roles(List<String> roles) {
                this.roles = roles;
                return this;
            }

            public TokenIntrospectionResponse build() {
                TokenIntrospectionResponse response = new TokenIntrospectionResponse();
                response.active = this.active;
                response.username = this.username;
                response.clientId = this.clientId;
                response.scope = this.scope;
                response.exp = this.exp;
                response.iat = this.iat;
                response.sub = this.sub;
                response.userId = this.userId;
                response.roles = this.roles != null ? this.roles : new ArrayList<>();
                return response;
            }
        }

        // Getters
        public boolean isActive() { return active; }
        public String getUsername() { return username; }
        public String getClientId() { return clientId; }
        public String getScope() { return scope; }
        public Long getExp() { return exp; }
        public Long getIat() { return iat; }
        public String getSub() { return sub; }
        public String getUserId() { return userId; }
        public List<String> getRoles() { return roles; }

        // Convenience method to check for DMS role
        public boolean hasDmsRole() {
            return roles.stream().anyMatch(role -> role.equalsIgnoreCase("dms") || role.contains("dms"));
        }

        public String getDmsRole() {
            return roles.stream()
                    .filter(role -> role.toLowerCase().contains("dms"))
                    .findFirst()
                    .orElse(null);
        }
    }
}
