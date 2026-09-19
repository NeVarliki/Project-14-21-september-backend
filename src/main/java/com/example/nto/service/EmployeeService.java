package com.example.nto.service;

import com.example.nto.controller.dto.UserDTO;

import java.util.Map;

public interface EmployeeService {
    UserDTO getByUsername(String username);
    Map<String, String> getList();
}
