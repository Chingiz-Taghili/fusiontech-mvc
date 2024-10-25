package com.multishop.fusiontech.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/admin/user")
    public String showIndexPage() {
        return "/admin/user/index";
    }

    @GetMapping("admin/user/create")
    public String showCreatePage() {
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser() {
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update")
    public String showUpdatePage() {
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser() {
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete")
    public String showDeletePage() {
        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser() {
        return "redirect:/admin/user";
    }
}
