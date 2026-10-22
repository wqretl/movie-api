package com.example.movieapi.service;



import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserRoleRequest;
import com.example.movieapi.dto.UserDto.UserStatusRequest;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.entity.Role;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.BusinessException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.UserMapper;
import com.example.movieapi.repository.RoleRepository;
import com.example.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

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

    public UserProfileResponse updateUserStatus(Long id,
            UserStatusRequest request
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));

        user.setEnabled(request.enabled());

        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileResponse(savedUser);
    }

    public UserProfileResponse addRoleToUser(Long id, UserRoleRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        Role role = roleRepository.findByName(request.role()).orElseThrow(()->new NotFoundException("Role","name",request.role()));

        user.addRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileResponse(savedUser);
    }

    public UserProfileResponse removeRoleFromUser(Long id, UserRoleRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        Role role = roleRepository.findByName(request.role()).orElseThrow(()->new NotFoundException("Role","name",request.role()));
        if (role.getName().equals("USER")) {
            throw new BusinessException("USER role cannot be removed");
        }
        user.removeRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileResponse(savedUser);
    }

}
