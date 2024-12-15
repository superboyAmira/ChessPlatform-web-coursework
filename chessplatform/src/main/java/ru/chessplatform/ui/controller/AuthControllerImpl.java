package ru.chessplatform.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chessplatform.domain.model.entity.Player;
import ru.chessplatform.domain.model.valueobject.RoleEnum;
import ru.chessplatform.domain.service.AuthService;

import javax.validation.Valid;
@Controller
@RequestMapping("/auth")
public class AuthControllerImpl {

    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // Шаблон страницы логина
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("player", new Player());
        return "auth/register"; // Шаблон страницы регистрации
    }

    @PostMapping("/register")
    public String registerPlayer(@ModelAttribute @Valid Player player, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.registerUser(player.getName(), player.getEmail(), player.getPassword(), RoleEnum.PLAYER, player.getChessGrade());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }

        return "redirect:/auth/login";
    }
}

