package com.example.springfilter.controller;

import com.example.springfilter.common.Const;
import com.example.springfilter.dto.LoginRequestDto;
import com.example.springfilter.dto.LoginResponseDto;
import com.example.springfilter.dto.SignupRequestDto;
import com.example.springfilter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
        userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request,
            HttpServletRequest httpRequest
    ) {
        LoginResponseDto response = userService.login(request);

        // 세션 생성 및 사용자 정보 저장
        HttpSession session = httpRequest.getSession();
        session.setAttribute(Const.SESSION_KEY, response.getUserId());

        return ResponseEntity.ok(response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }
}
