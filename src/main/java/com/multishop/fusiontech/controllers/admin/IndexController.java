package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showIndexPage(Principal principal, Model model) {
        model.addAttribute("searchUrl", "/admin/search/product");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/admin/index";
    }
}
