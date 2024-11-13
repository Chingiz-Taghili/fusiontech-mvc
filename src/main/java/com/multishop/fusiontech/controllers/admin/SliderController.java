package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.slider.SliderCreateDto;
import com.multishop.fusiontech.dtos.slider.SliderUpdateDto;
import com.multishop.fusiontech.services.SliderService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class SliderController {

    private final SliderService sliderService;
    private final UserService userService;

    public SliderController(SliderService sliderService, UserService userService) {
        this.sliderService = sliderService;
        this.userService = userService;
    }

    @GetMapping("/admin/slider")
    public String showIndexPage(Model model, Principal principal) {
        model.addAttribute("sliders", sliderService.getAllSliders());
        model.addAttribute("searchUrl", "/admin/search/slider");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/slider/index";
    }

    @GetMapping("/admin/search/slider")
    public String showSearchPage(String keyword, Model model, Principal principal) {
        model.addAttribute("sliders", sliderService.getSearchSliders(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchUrl", "/admin/search/slider");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));

        return "/admin/slider/index";
    }

    @GetMapping("/admin/slider/create")
    public String showCreatePage(Principal principal, Model model) {
        model.addAttribute("searchUrl", "/admin/search/slider");
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "/admin/slider/create";
    }

    @PostMapping("/admin/slider/create")
    public String createSlider(SliderCreateDto sliderCreateDto) {
        boolean result = sliderService.createSlider(sliderCreateDto);
        if (result) {
            return "redirect:/admin/slider";
        }
        return "redirect:/admin/slider/create";
    }

    @GetMapping("/admin/slider/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("slider", sliderService.getSliderById(id));
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("searchUrl", "/admin/search/slider");

        return "/admin/slider/update";
    }

    @PostMapping("/admin/slider/update/{id}")
    public String updateSlider(@PathVariable Long id, SliderUpdateDto sliderUpdateDto) {
        boolean result = sliderService.updateSlider(id, sliderUpdateDto);
        if (result) {
            return "redirect:/admin/slider";
        }
        return "redirect:/admin/slider/update/" + id;
    }

    @GetMapping("admin/slider/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("slider", sliderService.getSliderById(id));
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("searchUrl", "/admin/search/slider");

        return "/admin/slider/delete";
    }

    @PostMapping("/admin/slider/delete/{id}")
    public String deleteSlider(@RequestParam Long sliderId) {
        sliderService.deleteSlider(sliderId);
        return "redirect:/admin/slider";
    }
}
