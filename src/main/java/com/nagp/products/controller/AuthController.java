package com.nagp.products.controller;

import com.nagp.products.model.TokenRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/store-token")
    public ResponseEntity<?> storeToken(@RequestBody TokenRequest tokenRequest, HttpServletResponse response) {
        log.info("Received request for Store token");
        addCookie(response, "id_token", tokenRequest.getIdToken());
        addCookie(response, "access_token", tokenRequest.getAccessToken());
        addCookie(response, "refresh_token", tokenRequest.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "https://d1de3c8mspzt29.cloudfront.net");
        headers.add("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok().headers(headers).body("Tokens stored securely");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        log.info("Received request for logout");
        removeCookie(response, "id_token");
        removeCookie(response, "access_token");
        removeCookie(response, "refresh_token");

        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> checkAuthStatus(@CookieValue(name = "access_token", required = false) String token) {
        log.info("Received request for status {}", token);
        boolean isAuthenticated = (token != null);
        return ResponseEntity.ok(Collections.singletonMap("authenticated", isAuthenticated));
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getTokenFromCookie(@CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No token found"));
        }
        return ResponseEntity.ok(Map.of("access_token", accessToken));
    }


    private void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1 hour
        response.addCookie(cookie);
    }

    private void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
