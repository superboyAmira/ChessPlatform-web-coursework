package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/custom_second")
public interface PageSecondController extends BaseController {
    @GetMapping
    String showSecondPage();
}
