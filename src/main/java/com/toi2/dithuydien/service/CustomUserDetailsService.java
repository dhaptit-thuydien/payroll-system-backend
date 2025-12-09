package com.toi2.dithuydien.service;

import com.toi2.dithuydien.model.AppUser;
import com.toi2.dithuydien.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // Đã kiểm tra: Phải là @Service để Spring nhận diện là Bean
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- TRIỂN KHAI PHƯƠNG THỨC BẮT BUỘC CỦA USERDETAILSSERVICE ---
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Tìm kiếm AppUser trong Database
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User không tìm thấy với username: " + username)
                );

        // 2. Chuyển đổi AppUser thành đối tượng UserDetails của Spring Security
        // Lưu ý: UserDetails cần danh sách quyền (Authorities), hiện tại ta để trống.
        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(), // Password đã được mã hóa
                Collections.emptyList() // Danh sách quyền (Roles/Authorities)
        );
    }
}