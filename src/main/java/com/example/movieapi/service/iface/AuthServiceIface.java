package com.example.movieapi.service.iface;

import com.example.movieapi.dto.AuthResponse;
import com.example.movieapi.dto.LoginRequest;
import com.example.movieapi.dto.RegisterRequest;


public interface AuthServiceIface {

    AuthResponse userRegister(RegisterRequest request);

    AuthResponse userLogin(LoginRequest request);

    String refreshAccessToken (String refreshToken);

    void logout (String refreshToken);
}
