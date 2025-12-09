package com.toi2.dithuydien.model;


import jakarta.persistence.*;
// Lưu ý: Các thư viện JPA/Hibernate hiện tại dùng 'jakarta.persistence' (Spring Boot 3)

@Entity
@Table(name = "app_user") // Đã kiểm tra: Tên bảng trong DB
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Constructors
    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}