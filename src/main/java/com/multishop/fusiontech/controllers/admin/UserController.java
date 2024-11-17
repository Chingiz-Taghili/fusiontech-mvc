package com.multishop.fusiontech.controllers.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.multishop.fusiontech.dtos.user.UserCreateDto;
import com.multishop.fusiontech.dtos.user.UserUpdateDto;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final Cloudinary cloudinary;

    public UserController(UserService userService, Cloudinary cloudinary) {
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/admin/user")
    public String showIndexPage(Model model, Principal principal, Integer currentPage) {
        model.addAttribute("users", userService.getAllUsers(currentPage));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("urlType", "index");
        model.addAttribute("searchUrl", "/admin/search/user");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/user/index";
    }

    @GetMapping("/admin/search/user")
    public String showSearchPage(String keyword, Model model, Principal principal, Integer currentPage) {
        model.addAttribute("users", userService.getSearchUsers(keyword, currentPage));
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("urlType", "search");
        model.addAttribute("searchUrl", "/admin/search/user");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/user/index";
    }

    @GetMapping("admin/user/create")
    public String showCreatePage(Principal principal, Model model) {
        model.addAttribute("searchUrl", "/admin/search/user");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(UserCreateDto userCreateDto, @RequestParam MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            userCreateDto.setImage((String) uploadResult.get("url"));
        }
        boolean result = userService.createUser(userCreateDto);
        if (result) {
            return "redirect:/admin/user";
        } else {
            return "redirect:/admin/user/create";
        }
    }

    @GetMapping("/admin/user/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("userById", userService.getUserById(id));
        model.addAttribute("searchUrl", "/admin/search/user");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update/{id}")
    public String updateUser(@PathVariable Long id, UserUpdateDto userUpdateDto, @RequestParam MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            userUpdateDto.setImage((String) uploadResult.get("url"));
        }
        boolean result = userService.updateUser(id, userUpdateDto);
        if (result) {
            return "redirect:/admin/user";
        } else {
            return "redirect:/admin/user/update/" + id;
        }
    }

    @GetMapping("/admin/user/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("userById", userService.getUserById(id));
        model.addAttribute("searchUrl", "/admin/search/user");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/user";
    }
}
