package ru.chessplatform.ui.controller;

import com.example.controllers.AdminPageController;
import com.example.viewmodel.AdminViewModel;
import com.example.viewmodel.BaseViewModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class AdminPageControllerImpl implements AdminPageController {

    @Override
    public String showAdminPage(Model model) {
        // Создаем базовую модель
        BaseViewModel baseViewModel = createBaseViewModel("Admin Dashboard");
        model.addAttribute("base", baseViewModel);

        return "admin";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        // Получаем имя текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        // Возвращаем базовую модель
        return new BaseViewModel(title, currentUser);
    }
}
