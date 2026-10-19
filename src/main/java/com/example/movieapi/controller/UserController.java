package com.example.movieapi.controller;


import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;





    @GetMapping("/me")
    public ResponseEntity <UserProfileResponse> getCurrentUserProfile(){
       return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUserProfile());
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUserProfile(){
        userService.deleteCurrentUserProfile();
        return ResponseEntity.noContent().build();

    }

    @PatchMapping ("/me")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile ( @Valid @RequestBody UserUpdateRequest userUpdateRequest){

        UserProfileResponse userProfileResponse = userService
                .updateCurrentUserProfile( userUpdateRequest);

        return ResponseEntity.ok(userProfileResponse);

    }




}
