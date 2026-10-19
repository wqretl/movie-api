package com.example.movieapi.service;



import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.UserMapper;
import com.example.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserProfileResponse getCurrentUserProfile(){
        User user= getCurrentUser() ;
        return userMapper.toUserProfileResponse(user);

    }

    public UserProfileResponse getUserById( Long id){
        User user= userRepository.findById(id).orElseThrow(()->new NotFoundException("User",id));
        return userMapper.toUserProfileResponse(user);
    }

    public void deleteUserById( Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User",id));
        userRepository.delete(user);

    }



    public void deleteCurrentUserProfile(){
       User user = getCurrentUser();
       userRepository.delete(user);

    }

    public UserProfileResponse updateCurrentUserProfile( UserUpdateRequest request){

        User user = getCurrentUser();

        userMapper.updateUser(request,user);

        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileResponse(savedUser);
    }

    public UserProfileResponse updateUserById(Long id, UserUpdateRequest request){

        User user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User",id));

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

    public List<UserProfileResponse> getAllUsers(){
        List <User> users = userRepository.findAll();

        return userMapper.toUserProfileResponseList(users);
    }

}
