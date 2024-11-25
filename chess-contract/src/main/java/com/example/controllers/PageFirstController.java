package com.example.controllers;

import com.example.viewmodel.CustomPage1ViewModel;
import org.springframework.ui.Model;

public interface PageFirstController extends BaseController {
    CustomPage1ViewModel showFirstPage(Model model);
}
