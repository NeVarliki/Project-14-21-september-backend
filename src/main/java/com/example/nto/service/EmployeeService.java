package com.example.nto.service;

import com.example.nto.controller.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getByCode(String code);

    void auth(String code);
}
