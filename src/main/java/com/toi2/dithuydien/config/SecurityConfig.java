package com.toi2.dithuydien.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ... các import khác ...
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// ...

import com.toi2.dithuydien.service.CustomUserDetailsService; // DÒNG THÊM VÀO

// Lưu ý: Các import đã được kiểm tra tương thích với Spring Boot 3.x (jakarta)

@Configuration
@EnableWebSecurity


public class SecurityConfig {




    // THÊM 2 FIELD + CONSTRUCTOR (dán lên đầu class, ngay dưới @EnableWebSecurity)
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }




    // --- BEAN 1: KHAI BÁO AUTHENTICATIONMANAGER (Khắc phục lỗi cũ) ---
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // --- BEAN 2: KHAI BÁO PASSWORDENCODER (BẮT BUỘC cho Đăng ký) ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Tắt CSRF vì đang sử dụng Token (phù hợp với REST API)
                .csrf(csrf -> csrf.disable())
                // 2. Cấu hình quản lý Session là STATELESS (BẮT BUỘC cho JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 3. Định nghĩa quy tắc ủy quyền (Authorization)
                .authorizeHttpRequests(authorize -> authorize
                        // Cho phép truy cập CÔNG KHAI các Endpoint Đăng ký/Đăng nhập
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()  // nếu dùng H2 console
                        // Yêu cầu xác thực cho TẤT CẢ các request khác
                        .anyRequest().authenticated()
                )
                // QUAN TRỌNG NHẤT: THÊM FILTER JWT VÀO TRƯỚC UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Cho H2 console (nếu dùng)

                // DÒNG NÀY LÀ DÒNG BỊ LỖI → SỬA THÀNH DÒNG DƯỚI
                // .headers(headers -> headers.frameOptions().disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // ← ĐÚNG 100%


        // Đăng ký provider
        http.authenticationProvider(authenticationProvider());
        // Lưu ý: Chúng ta sẽ thêm JwtAuthenticationFilter vào đây sau khi tạo nó.
        return http.build();
    }


    // PHƯƠNG THỨC KHUYẾN NGHỊ: CẤU HÌNH AUTHENTICATION PROVIDER
    // DaoAuthenticationProvider sử dụng UserDetailsService và PasswordEncoder của bạn

    // SỬA phương thức authenticationProvider (xóa tham số, dùng field)
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}