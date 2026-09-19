package com.example.nto.controller;

import com.example.nto.controller.dto.InfoDTO;
import com.example.nto.controller.dto.UserDTO;
import com.example.nto.entity.User;
import com.example.nto.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Validated
public class UserController {

    private final EmployeeService employeeService;


    @GetMapping("/info")
    public UserDTO getByUsername(@RequestBody InfoDTO infoDTO, @AuthenticationPrincipal User user) {
        if(infoDTO.username() == null){
            return employeeService.getByUsername(user.getUsername());
        } else {
            return employeeService.getByUsername(infoDTO.username());
        }
    }
    @GetMapping("/list")
    public Map<String, String> getList(){
        return employeeService.getList();
    }
}
