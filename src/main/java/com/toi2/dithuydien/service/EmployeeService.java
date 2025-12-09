package com.toi2.dithuydien.service;


import com.toi2.dithuydien.model.Employee;
import com.toi2.dithuydien.model.Department;
import com.toi2.dithuydien.repository.EmployeeRepository;
import com.toi2.dithuydien.repository.DepartmentRepository;
import com.toi2.dithuydien.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    // Dependency Injection (DI) qua Constructor
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }



    // Ví dụ về phương thức tìm Employee theo ID
    public Employee getEmployeeById(Long id) {
        // Sử dụng orElseThrow để ném ngoại lệ tùy chỉnh
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
    }


    // --- CÁC PHƯƠNG THỨC CRUD (GIỮ NGUYÊN) ---
    // public Employee createEmployee(Employee employee) { ... }
    // public Employee getEmployeeById(Long id) { ... }
    // public List<Employee> getAllEmployees() { ... }
    // public void deleteEmployee(Long id) { ... }

    // --------------------------------------------------------------------------
    // --- PHƯƠNG THỨC NGHIỆP VỤ MỚI (SỬ DỤNG TRUY VẤN TÙY CHỈNH) ---

    // 1. Tìm nhân viên theo ID phòng ban
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        // Tự động sử dụng phương thức findByDepartmentId() từ Repository
        return employeeRepository.findByDepartmentId(departmentId);
    }

    // 2. Tìm nhân viên theo từ khóa trong tiêu đề công việc
    public List<Employee> searchEmployeesByTitle(String keyword) {
        // Tự động sử dụng phương thức findByTitleContainingIgnoreCase()
        return employeeRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // 3. Tìm nhân viên bắt đầu làm việc sau một ngày cụ thể
    public List<Employee> getEmployeesStartedAfter(LocalDate date) {
        // Sử dụng phương thức findByStartDateAfterOrderByNameAsc()
        return employeeRepository.findByStartDateAfterOrderByNameAsc(date);
    }

    // 4. Tìm nhân viên theo Tên Phòng ban (Sử dụng @Query)
    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        // Sử dụng phương thức @Query đã định nghĩa
        return employeeRepository.findEmployeesByDepartmentName(departmentName);
    }
}