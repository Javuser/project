package com.example.project.service;

public interface UserService {

    void registerUserByEmail(String email);
    boolean authenticateUser(String login, String password);
}
