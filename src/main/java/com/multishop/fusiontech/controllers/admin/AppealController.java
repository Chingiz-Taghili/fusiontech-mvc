package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.AppealService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AppealController {

    private final AppealService appealService;
    private final UserService userService;

    public AppealController(AppealService appealService, UserService userService) {
        this.appealService = appealService;
        this.userService = userService;
    }

    @GetMapping("/admin/appeal")
    public String showIndexPage(Integer currentPage, Model model, Principal principal) {
        model.addAttribute("appeals", appealService.getAllAppeals(currentPage));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("urlType", "index");
        model.addAttribute("searchUrl", "/admin/search/appeal");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/appeal/index";
    }

    @GetMapping("/admin/search/appeal")
    public String showSearchPage(String keyword, Integer currentPage, Model model, Principal principal) {
        model.addAttribute("appeals", appealService.getSearchAppeals(keyword, currentPage));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("urlType", "search");
        model.addAttribute("searchUrl", "/admin/search/appeal");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/appeal/index";
    }
}
