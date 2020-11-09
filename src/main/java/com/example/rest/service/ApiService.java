package com.example.rest.service;

import com.example.rest.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers(Integer limit);
}
