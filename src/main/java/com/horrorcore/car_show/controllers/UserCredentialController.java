package com.horrorcore.car_show.controllers;

import com.horrorcore.car_show.dtos.UserRequest;
import com.horrorcore.car_show.services.UserCredentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserCredentialController {
    private final UserCredentialService userCredentialService;

    @GetMapping(value = "/register")
    public String getUserForm(Model model) {
        model.addAttribute("newUser", new UserRequest("",""));
        return "user/form";
    }

    @PostMapping(value = "/register")
    public String postUserForm(@Valid @ModelAttribute UserRequest userRequest, Errors errors) {
        if(errors.hasErrors()) {
            return "user/form";
        }
        userCredentialService.createUser(userRequest);
        return "redirect:/car";
    }
}
