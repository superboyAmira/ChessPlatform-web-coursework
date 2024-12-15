package com.example.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/custom_first")
public interface PageFirstController extends BaseController {

    @GetMapping
    String showFirstPage(Model model);
}
