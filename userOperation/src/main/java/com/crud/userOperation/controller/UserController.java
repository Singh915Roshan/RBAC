package com.crud.userOperation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.crud.userOperation.dto.UserDto;
import com.crud.userOperation.service.UserService;

@RestController
	@RequestMapping("/api/users")
	public class UserController {

	    @Autowired
	    private UserService userService;

	    @PostMapping
	    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
	        try {
	            userService.createUser(userDto);
	            return ResponseEntity.ok("User created successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to create user: " + e.getMessage());
	        }
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<?> getUserById(@PathVariable Long id) {
	        try {
	            UserDto userDto = userService.getUserById(id);
	            return ResponseEntity.ok(userDto);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("User not found: " + e.getMessage());
	        }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
	        try {
	            userService.updateUser(id, userDto);
	            return ResponseEntity.ok("User updated successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to update user: " + e.getMessage());
	        }
	    }

	    @PatchMapping("/{id}")
	    public ResponseEntity<?> updatePartialUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
	        try {
	            userService.updatePartialUser(id, updates);
	            return ResponseEntity.ok("User attributes updated successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to update user attributes: " + e.getMessage());
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	        try {
	            userService.deleteUser(id);
	            return ResponseEntity.ok("User deleted successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to delete user: " + e.getMessage());
	        }
	    }
	}

