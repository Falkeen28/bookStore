package com.bookStore.bookStore.controller;

import com.bookStore.bookStore.model.MyUserDTO;
import com.bookStore.bookStore.service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MyUserService myUserService;

    @RequestMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new MyUserDTO());
        return "registration_page";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid MyUserDTO myUserDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", myUserDTO);
            return "registration_page";
        }
        myUserService.register(myUserDTO);
        return "redirect:/login?success";
    }
}