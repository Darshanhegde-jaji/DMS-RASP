//package com.rasp.dms.security;
//
//import com.rasp.dms.util.JwtTokenUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String requestTokenHeader = request.getHeader("Authorization");
//        String jwtToken = null;
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//        } else {
//            jwtToken = requestTokenHeader;
//        }
//
//        // If token is present, validate it with Keycloak
//        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            JwtTokenUtil.TokenIntrospectionResponse tokenResponse = jwtTokenUtil.introspectToken(jwtToken);
//
//            if (tokenResponse.isActive()) {
//                String username = tokenResponse.getUsername();
//                String userId = tokenResponse.getUserId();
//                List<String> userRoles = tokenResponse.getRoles();
//                String dmsRole = tokenResponse.getDmsRole();
//
//                // Create UserContext and set it in ThreadLocal
//                UserContext userContext = new UserContext(userId, username, userRoles, dmsRole);
//                UserContextHolder.setContext(userContext);
//
//                // Parse authorities from scope if available
//                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                if (tokenResponse.getScope() != null) {
//                    authorities.addAll(Arrays.stream(tokenResponse.getScope().split(" "))
//                            .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
//                            .collect(Collectors.toList()));
//                }
//
//                // Add roles as authorities
//                authorities.addAll(userRoles.stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                        .collect(Collectors.toList()));
//
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(username, null, authorities);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        try {
//            filterChain.doFilter(request, response);
//        } finally {
//            // Clear the context after request processing
//            UserContextHolder.clearContext();
//        }
//    }
//}