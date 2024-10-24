package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.AppealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppealController {

    private final AppealService appealService;

    public AppealController(AppealService appealService) {
        this.appealService = appealService;
    }

    @GetMapping("/admin/appeal")
    public String showIndexPage(Integer currentPage, Model model) {
        PaginationPayload<AppealDto> appeals = appealService.getAdminAppeals(currentPage);
        model.addAttribute("appeals", appeals);
        model.addAttribute("currentPage", currentPage);
        return "/admin/appeal/index";
    }
}
