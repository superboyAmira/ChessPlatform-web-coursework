package com.example.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/")
public interface MainController extends BaseController {
    @GetMapping
    String showHomePage(Model model);
}



