package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.slider.SliderCreateDto;
import com.multishop.fusiontech.dtos.slider.SliderHomeDto;
import com.multishop.fusiontech.dtos.slider.SliderUpdateDto;
import com.multishop.fusiontech.services.SliderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SliderController {

    private final SliderService sliderService;

    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping("/admin/slider")
    public String showIndexPage(Model model) {
        List<SliderHomeDto> sliders = sliderService.getHomeSliders();
        model.addAttribute("sliders", sliders);
        return "/admin/slider/index";
    }

    @GetMapping("/admin/slider/create")
    public String showCreatePage() {
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
    public String showUpdatePage(@PathVariable Long id, Model model) {
        SliderHomeDto slider = sliderService.getSliderById(id);
        model.addAttribute("slider", slider);
        return "/admin/slider/update";
    }

    @PostMapping("/admin/slider/update/{id}")
    public String updateSlider(@PathVariable Long id, SliderUpdateDto sliderUpdateDto) {
        boolean result = sliderService.updateSlider(id, sliderUpdateDto);
        if (result) {
            return "redirect:/admin/slider";
        }
        return "redirect:/admin/slider/update";
    }

    @GetMapping("admin/slider/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model) {
        SliderHomeDto slider = sliderService.getSliderById(id);
        model.addAttribute("slider", slider);
        return "/admin/slider/delete";
    }

    @PostMapping("/admin/slider/delete/{id}")
    public String deleteSlider(@RequestParam Long sliderId) {
        sliderService.deleteSlider(sliderId);
        return "redirect:/admin/slider";
    }
}
