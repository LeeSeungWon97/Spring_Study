package toy.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 웰컴 페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
