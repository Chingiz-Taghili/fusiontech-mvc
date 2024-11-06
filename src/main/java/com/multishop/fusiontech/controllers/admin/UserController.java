package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/user")
    public String showIndexPage(Model model, Principal principal, Integer currentPage) {
        PaginationPayload<UserDto> users = userService.getAllUsers(currentPage);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchUrl", "/admin/search/user");

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/user/index";
    }

    @GetMapping("admin/user/create")
    public String showCreatePage(Principal principal, Model model) {
        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(UserCreateDto userCreateDto) {
        boolean result = userService.createUser(userCreateDto);

        if (result) {
            return "redirect:/admin/user";
        } else {
            return "redirect:/admin/user/create";
        }
    }

    @GetMapping("/admin/user/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        UserDto userById = userService.getUserById(id);
        model.addAttribute("userById", userById);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update/{id}")
    public String updateUser(@PathVariable Long id, UserUpdateDto userUpdateDto) {
        boolean result = userService.updateUser(id, userUpdateDto);

        if (result) {
            return "redirect:/admin/user";
        } else {
            return "redirect:/admin/user/update";
        }
    }

    @GetMapping("/admin/user/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        UserDto userById = userService.getUserById(id);
        model.addAttribute("userById", userById);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/user";
    }
}
