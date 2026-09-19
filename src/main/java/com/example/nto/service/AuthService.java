package com.example.nto.service;

import com.example.nto.controller.dto.AuthRequestDTO;
import com.example.nto.controller.dto.AuthResponseDTO;
import com.example.nto.controller.dto.RefreshDTO;
import com.example.nto.entity.User;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO requestDTO);
    AuthResponseDTO refresh(RefreshDTO refreshDTO);
}
