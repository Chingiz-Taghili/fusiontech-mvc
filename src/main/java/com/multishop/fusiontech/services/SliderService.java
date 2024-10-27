package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.slider.SliderCreateDto;
import com.multishop.fusiontech.dtos.slider.SliderHomeDto;
import com.multishop.fusiontech.dtos.slider.SliderUpdateDto;

import java.util.List;

public interface SliderService {

    List<SliderHomeDto> getHomeSliders();

    SliderHomeDto getSliderById(Long id);

    boolean createSlider(SliderCreateDto sliderCreateDto);

    boolean updateSlider(Long id, SliderUpdateDto sliderUpdateDto);

    void deleteSlider(Long id);
}
