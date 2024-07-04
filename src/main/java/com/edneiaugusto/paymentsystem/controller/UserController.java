package com.edneiaugusto.paymentsystem.controller;

import com.edneiaugusto.paymentsystem.system.user.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserVerifyService userVerifyService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        User user = userService.findById(id);
        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(UserResponse.fromEntity(user));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.registerUser(userRequest.asEntitty());
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @GetMapping("/verify/{id}/{code}")
    public ResponseEntity<?> verify(@PathVariable UUID id, @PathVariable String code) {
        try {
            userVerifyService.execute(UserVerifyService.Input.builder().id(id).code(code).build());
            return ResponseEntity.ok("verify_sucess");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
