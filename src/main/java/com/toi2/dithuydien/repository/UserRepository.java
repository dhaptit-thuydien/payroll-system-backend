package com.toi2.dithuydien.repository;

import com.toi2.dithuydien.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Kế thừa JpaRepository để có sẵn các hàm CRUD cơ bản
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Đã kiểm tra: Hàm JPA Custom Method BẮT BUỘC cho AuthController
    // Spring Data JPA sẽ tự động tạo truy vấn SQL từ tên hàm này.
    Boolean existsByUsername(String username);

    // Hàm tìm kiếm user theo username
    Optional<AppUser> findByUsername(String username);
}