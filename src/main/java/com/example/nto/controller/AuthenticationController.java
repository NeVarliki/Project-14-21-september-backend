package com.example.nto.controller;


import com.example.nto.controller.dto.AuthRequestDTO;
import com.example.nto.controller.dto.AuthResponseDTO;
import com.example.nto.controller.dto.RefreshDTO;
import com.example.nto.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return authService.login(authRequestDTO);
    }
    @PostMapping("/refresh")
    public AuthResponseDTO refresh(@RequestBody @Valid RefreshDTO refreshDTO){
        return authService.refresh(refreshDTO);
    }

}
