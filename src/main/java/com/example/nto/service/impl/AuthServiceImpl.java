package com.example.nto.service.impl;

import com.example.nto.controller.dto.AuthRequestDTO;
import com.example.nto.controller.dto.AuthResponseDTO;
import com.example.nto.controller.dto.RefreshDTO;
import com.example.nto.entity.User;
import com.example.nto.exception.InvalidRefreshToken;
import com.example.nto.exception.UserNotFoundException;
import com.example.nto.repository.UserRepository;
import com.example.nto.service.AuthService;
import com.example.nto.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthResponseDTO login(AuthRequestDTO requestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.username(), requestDTO.password()));
        User user = userRepository.findByUsername(requestDTO.username())
                .orElseThrow(() -> new UserNotFoundException("Can not find user with username " + requestDTO.username() + " to log in"));

        return new AuthResponseDTO(jwtService.genAccess(user), jwtService.genRefresh(user));
    }

    @Override
    @Transactional
    public AuthResponseDTO refresh(RefreshDTO refreshDTO) {
        User user = userRepository.findByUsername(jwtService.getClaim(refreshDTO.refresh(), Claims::getSubject))
                .orElseThrow(() -> new UserNotFoundException("Can not find user to log in"));
        if(jwtService.isValidRefresh(refreshDTO.refresh(), user)){
            return new AuthResponseDTO(jwtService.genAccess(user), jwtService.genRefresh(user));
        } else{
            throw new InvalidRefreshToken("Your refresh token is invalid");
        }
    }
}
