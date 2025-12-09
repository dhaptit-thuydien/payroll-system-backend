package com.toi2.dithuydien.controller;

import com.toi2.dithuydien.model.Employee;
import com.toi2.dithuydien.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // IMPORT CHÍNH XÁC

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

//Đây là phương pháp tốt nhất để tiêm (Inject) đối tượng //EmployeeService vào Controller.

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ... CÁC ENDPOINT CRUD CŨ GIỮ NGUYÊN ...

    // --- CÁC ENDPOINT TRUY VẤN TÙY CHỈNH MỚI ---


    // GET /api/employees/by-dept?deptId=1. Khi postmand goi đúng url này thi no se tra ve 1 hàm của lớp logic employeeService là getEmployeesByDepartmentId


    @GetMapping("/by-dept")
    public List<Employee> getEmployeesByDept(@RequestParam Long deptId) {
        return employeeService.getEmployeesByDepartmentId(deptId);
    }

    // GET /api/employees/search?keyword=Engineer
    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return employeeService.searchEmployeesByTitle(keyword);
    }

    // GET /api/employees/started-after?date=2024-01-01
    @GetMapping("/started-after")
    public List<Employee> getEmployeesStartedAfter(@RequestParam LocalDate date) {
        return employeeService.getEmployeesStartedAfter(date);
    }

    // GET /api/employees/by-dept-name?deptName=IT Payroll
    @GetMapping("/by-dept-name")
    public List<Employee> getEmployeesByDeptName(@RequestParam String deptName) {
        return employeeService.getEmployeesByDepartmentName(deptName);
    }
}

