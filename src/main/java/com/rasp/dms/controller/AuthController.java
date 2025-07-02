//package com.rasp.dms.controller;
//
//
//
//import com.rasp.dms.util.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
//public class AuthController {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
////        // In a real application, you would validate credentials against Keycloak
////        // For demo purposes, we'll generate a token for any valid username
////
////        if (loginRequest.getUsername() != null && !loginRequest.getUsername().isEmpty()) {
////            String token = jwtTokenUtil.generateToken(loginRequest.getUsername());
////            return ResponseEntity.ok(new LoginResponse(token, "Bearer", loginRequest.getUsername()));
////        }
////
////        return ResponseEntity.badRequest().body("Invalid credentials");
////    }
//
//    @PostMapping("/validate")
//    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
//        try {
//            String jwtToken = token;
//            if (jwtTokenUtil.validateToken(jwtToken)) {
//                return ResponseEntity.ok().body("Token is valid for user: ");
//            } else {
//                return ResponseEntity.badRequest().body("Invalid token");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid token format");
//        }
//    }
//}