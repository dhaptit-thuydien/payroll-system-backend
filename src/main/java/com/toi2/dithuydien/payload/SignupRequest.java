package com.toi2.dithuydien.payload;

// Đây là lớp DTO/Request Model dùng để nhận dữ liệu từ Client (Postman) khi Đăng ký
public class SignupRequest {

    private String username;
    private String password;

    // 1. Constructor không tham số (Bắt buộc cho nhiều Framework như Spring/Jackson)
    public SignupRequest() {
    }

    // 2. Constructor có tham số (Chỉ giữ lại 1 bản sạch, chính xác)
    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    // Getters và Setters
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