package com.toi2.dithuydien.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull; // IMPORT QUAN TRỌNG ĐỂ KHẮC PHỤC LỖI
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Dán ngay sau dòng @Component
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Lấy JWT từ request Header
        String token = getJwtFromRequest(request);

        // 2. Xác thực và Thiết lập Authentication
        // Kiểm tra token có tồn tại và hợp lệ không
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

            String username = tokenProvider.getUsername(token);

            // Load thông tin người dùng
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Tạo đối tượng Authentication
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Thiết lập Authentication vào Spring Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Cho phép request đi tiếp qua các filters khác
        filterChain.doFilter(request, response);
    }

    // Hàm phụ trợ: Lấy JWT từ Header Authorization
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bỏ qua "Bearer "
        }
        return null;
    }
}