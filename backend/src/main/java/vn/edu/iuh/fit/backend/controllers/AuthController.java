/*
 * @ {#} AuthController.java   1.0     8/14/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.backend.dtos.request.LoginRequest;
import vn.edu.iuh.fit.backend.dtos.response.BaseResponse;
import vn.edu.iuh.fit.backend.services.JwtService;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/14/2025
 * @version:    1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtService.generateToken(authentication.getName());
            return ResponseEntity.ok(
                    new BaseResponse<>("success", "Login successful", token)
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(
                    new BaseResponse<>("error", "Invalid username or password", null)
            );
        }
    }
}

