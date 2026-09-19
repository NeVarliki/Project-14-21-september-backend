package com.example.nto.service.impl;

import com.example.nto.controller.dto.UserDTO;
import com.example.nto.entity.User;
import com.example.nto.exception.UserNotFoundException;
import com.example.nto.repository.BookingRepository;
import com.example.nto.repository.UserRepository;
import com.example.nto.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDTO getByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(("User was not found by this username: " + username)));
        return UserDTO.toDto(user, bookingRepository.findAllByUser(user));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getList() {
        var users = userRepository.findAll();
        Map<String, String> result = new HashMap<>();
        for(User user : users){
            result.put(user.getUsername(), user.getName());
        }
        return result;
    }


}
