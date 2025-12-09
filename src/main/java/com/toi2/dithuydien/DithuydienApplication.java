package com.toi2.dithuydien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController; // 👈 thêm dòng này


@SpringBootApplication
public class DithuydienApplication {

	public static void main(String[] args) {
		SpringApplication.run(DithuydienApplication.class, args);
	}

}

@RestController
class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Xin chào từ 2toi.com - Dự án Dithuydien!";
    }
}