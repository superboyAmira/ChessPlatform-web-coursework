package com.example.controllers;

import com.example.viewmodel.AdminViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public interface AdminPageController extends BaseController {
    @GetMapping
    String showAdminPage(Model model);
}

