package com.example.springfilter.service;

import com.example.springfilter.common.exception.DuplicateException;
import com.example.springfilter.common.exception.UnauthorizedException;
import com.example.springfilter.dto.LoginRequestDto;
import com.example.springfilter.dto.LoginResponseDto;
import com.example.springfilter.dto.SignupRequestDto;
import com.example.springfilter.entity.User;
import com.example.springfilter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateException("이미 존재하는 사용자입니다.");
        }

        User user = new User(request.getUsername(), request.getPassword());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponseDto(user.getId());
    }
}
