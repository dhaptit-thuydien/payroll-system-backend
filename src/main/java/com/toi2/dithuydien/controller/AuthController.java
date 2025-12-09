package com.toi2.dithuydien.controller;


import com.toi2.dithuydien.model.AppUser;
import com.toi2.dithuydien.payload.LoginRequest;
import com.toi2.dithuydien.payload.SignupRequest;
import com.toi2.dithuydien.payload.JwtResponse;
import com.toi2.dithuydien.repository.UserRepository;
import com.toi2.dithuydien.config.JwtTokenProvider; // Đã kiểm tra: Tên Class đã khớp
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth") // Đã kiểm tra: Base path khớp với yêu cầu Postman
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    // PHƯƠNG PHÁP TIÊM DEPENDENCY KHUYẾN NGHỊ (Constructor Injection)
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // --- ENDPOINT 1: ĐĂNG NHẬP (LOGIN) ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 1. Xác thực người dùng bằng Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // 2. Thiết lập Context Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. TẠO JWT TOKEN (Sử dụng Bean JwtTokenProvider)
        String jwt = tokenProvider.generateToken(authentication);

        // 4. Trả về Token và thông tin User
        return ResponseEntity.ok(new JwtResponse(jwt, loginRequest.getUsername()));
    }

    // --- ENDPOINT 2: ĐĂNG KÝ (SIGNUP) ---
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        // 1. Kiểm tra Username đã tồn tại chưa
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username đã được sử dụng!");
        }

        // 2. Tạo User Mới (Giả định bạn có Model AppUser)
        AppUser user = new AppUser(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword())); // Mã hóa Mật khẩu

        // 3. Lưu vào Database
        userRepository.save(user);

        return ResponseEntity.ok("Đăng ký thành công!");
    }
}