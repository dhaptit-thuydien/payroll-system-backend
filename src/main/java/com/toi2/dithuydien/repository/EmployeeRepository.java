package com.toi2.dithuydien.repository;

import com.toi2.dithuydien.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// CÁC IMPORT BẠN CẦN THÊM VÀ DI CHUYỂN LÊN ĐÂY
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // --- QUERY METHOD NAMING ---

    // 1. Tìm tất cả nhân viên thuộc một phòng ban cụ thể
    List<Employee> findByDepartmentId(Long departmentId);

    // 2. Tìm tất cả nhân viên có tiêu đề công việc chứa một chuỗi
    List<Employee> findByTitleContainingIgnoreCase(String keyword);

    // 3. Tìm nhân viên bắt đầu làm việc sau một ngày cụ thể
    List<Employee> findByStartDateAfterOrderByNameAsc(LocalDate date);


    // --- TRUY VẤN TÙY CHỈNH BẰNG @QUERY (JPQL) ---

    // Sử dụng JPQL để JOIN qua Department và tìm kiếm theo tên phòng ban
    @Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :departmentName")
    List<Employee> findEmployeesByDepartmentName(@Param("departmentName") String departmentName);

}