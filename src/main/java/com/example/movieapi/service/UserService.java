package com.example.movieapi.service;


import com.example.movieapi.dto.UserDto.UserCreatedRequest;
import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.DuplicateResourceException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.exception.ResourceNotFoundException;
import com.example.movieapi.mapper.UserMapper;
import com.example.movieapi.repository.RoleRepository;
import com.example.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserProfileResponse getUserById(Long id){
        User user=  userRepository.findById(id).orElseThrow(()->new NotFoundException("User",id));
        return userMapper.toUserProfileResponse(user);

    }



    public void deleteUserById(Long id){
       User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User",id));
       userRepository.delete(user);

    }

    public UserProfileResponse updateUser(Long id, UserUpdateRequest request){

        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User",id));

        userMapper.updateUser(request,user);

        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileResponse(savedUser);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       String email = authentication.getName();


       User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User", "email", email));

       return user;
    }

}
