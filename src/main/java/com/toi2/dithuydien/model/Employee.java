package com.toi2.dithuydien.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity // Đánh dấu đây là một JPA Entity
@Table(name = "employees") // Ánh xạ tới bảng 'employees'
public class Employee implements Serializable {
    // --- Khóa Chính (Primary Key) ---
    @Id // Đánh dấu trường này là Khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng (chuẩn PostgreSQL)
    private Long id;

    // --- Các Cột Dữ liệu (Columns) ---
    @Column(name = "full_name", nullable = false, length = 100)
    private String name;

    @Column(name = "job_title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    // --- Khóa Ngoại (Many-to-One Relationship) ---
    @ManyToOne(fetch = FetchType.LAZY) // N:1 là kiểu LAZY (load khi cần) là chuẩn
    @JoinColumn(name = "department_id", nullable = false) // Tên cột Khóa Ngoại trong bảng employees
    private Department department;

    // --------------------------------------------------------------------------
    // --- Constructors ---

    // Bắt buộc phải có Constructor không tham số (No-args Constructor)
    public Employee() {
    }

    // Constructor đầy đủ
    public Employee(String name, String title, LocalDate startDate, Department department) {
        this.name = name;
        this.title = title;
        this.startDate = startDate;
        this.department = department;
    }

    // --------------------------------------------------------------------------
    // --- GETTER & SETTER ---

    public Long getId() { return id; }
    // Setter cho ID thường không cần thiết

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
