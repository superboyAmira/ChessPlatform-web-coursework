package ru.chessplatform.ui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @GetMapping
    public String showProfile(Model model) {
        LOG.info("profile showed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Имя пользователя
        String details = authentication.getDetails().toString();
        model.addAttribute("username", username);

        model.addAttribute("role", authentication.getAuthorities().stream()
                .map(Object::toString)
                .findFirst()
                .orElse("NO_ROLE"));

        return "player/profile";
    }
}
