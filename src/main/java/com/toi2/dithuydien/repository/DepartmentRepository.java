package com.toi2.dithuydien.repository;


import com.toi2.dithuydien.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Kế thừa JpaRepository cho Entity Department (với ID là Long)
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Phương thức này rất hữu ích khi làm việc với logic nghiệp vụ
    // Ví dụ: Kiểm tra phòng ban có tồn tại không trước khi tạo nhân viên
    Department findByName(String name);

    // Có thể thêm các truy vấn tùy chỉnh khác ở đây
}