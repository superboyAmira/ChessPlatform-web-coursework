package com.example.controllers;

import com.example.viewmodel.MainPageViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/")
public interface MainController extends BaseController {

    @GetMapping
    String showHomePage(Model model);
}



