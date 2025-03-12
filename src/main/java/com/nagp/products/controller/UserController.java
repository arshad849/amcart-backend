package com.nagp.products.controller;

import com.nagp.products.entity.User;
import com.nagp.products.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    // POST /users: Sync user data after Cognito registration
    @PostMapping
    public ResponseEntity<String> syncUserData(@RequestBody User userData) {
        log.info("Received request from post confirmation lambda to user {}", userData);
        service.syncUserData(userData);
        return new ResponseEntity<>("User data synced successfully", HttpStatus.CREATED);
    }

    // GET /users/{id}: Retrieve user details
    @GetMapping("/{id}")
    public User getUserDetails(@PathVariable("id") String userId) {
        log.info("Received request to get user details for userId {}", userId);
        return service.getUser(userId);
    }

    // PUT /users/{id}: Update user profile
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserProfile(@PathVariable("id") String userId, @RequestBody User user) {

        return ResponseEntity.ok("User profile updated successfully");
    }

    // DELETE /users/{id}: Delete user from the database and optionally from Cognito
    /*@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId, @RequestParam(value = "deleteFromCognito", required = false, defaultValue = "false") boolean deleteFromCognito) {
        // Logic to delete user
        if (deleteFromCognito) {
            // Additional logic to delete user from Cognito
        }
        return ResponseEntity.ok("User deleted successfully");
    }

    // GET /users: List users (admin-only endpoint)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        // Logic to list users
        return ResponseEntity.ok(users);
    }*/


}
