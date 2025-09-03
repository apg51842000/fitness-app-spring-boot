package com.fitnessapp.demo.controller;

import com.fitnessapp.demo.dto.UserRequest;
import com.fitnessapp.demo.dto.UserResponse;
import com.fitnessapp.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request){
        UserResponse newUser = userService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        UserResponse user = userService.getProfileId(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId){
        Boolean isValidUser = userService.validateUser(userId);
        return ResponseEntity.ok(isValidUser);
    }
}
