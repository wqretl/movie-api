package com.example.movieapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Имя обязательно!")
    private String fullName;

    @NotBlank(message = "Email обязателен!")
    private String email;

    @NotBlank(message = "Username обязателен!")
    private String username;

    //todo в редисе будем хранить username для
    // быстрой проверки уникальности на этапе регистрации или редактирование username

    @Size(min = 8, message = "Пароль должен содержать больше 8-ми символов")
    private String password;

    @NotBlank(message = "Повтор пароля обязателен!")
    private String confirmPassword;
}