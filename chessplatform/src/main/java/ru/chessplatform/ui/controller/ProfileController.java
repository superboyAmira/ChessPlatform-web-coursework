package ru.chessplatform.ui.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Имя пользователя
        model.addAttribute("username", username);

        // Здесь можно добавить больше данных пользователя
        model.addAttribute("role", authentication.getAuthorities().stream()
                .map(Object::toString)
                .findFirst()
                .orElse("USER")); // Роль пользователя (если есть)

        return "profile";
    }
}
