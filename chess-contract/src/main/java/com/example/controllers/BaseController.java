package com.example.controllers;

import com.example.viewmodel.BaseViewModel;

public interface BaseController {
    BaseViewModel createBaseViewModel(String title);
}