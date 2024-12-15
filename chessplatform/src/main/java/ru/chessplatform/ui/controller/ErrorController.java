package ru.chessplatform.ui.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/auth/forbidden")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Access Denied");
        return "auth/forbidden";
    }
}
