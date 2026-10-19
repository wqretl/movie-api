package com.example.movieapi.controller.admin;


import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

        private final UserService userService;

        @GetMapping("/{id}")
        public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id){

            return ResponseEntity.ok(userService.getUserById(id));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();

        }

        @PatchMapping ("/{id}")
        public ResponseEntity<UserProfileResponse> updateUser (@PathVariable Long id , @Valid @RequestBody UserUpdateRequest userUpdateRequest){

            UserProfileResponse userProfileResponse = userService.updateUserById(id, userUpdateRequest);

            return ResponseEntity.ok(userProfileResponse);

        }

        @GetMapping ("/me-test")
        public String testCurrentUser( Authentication authentication){

            return authentication.getName();
        }

        @GetMapping
        public ResponseEntity<List<UserProfileResponse>> getAllUsers(){
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        }





}
