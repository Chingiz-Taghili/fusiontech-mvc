package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.slider.SliderCreateDto;
import com.multishop.fusiontech.dtos.slider.SliderDto;
import com.multishop.fusiontech.dtos.slider.SliderUpdateDto;

import java.util.List;

public interface SliderService {

    List<SliderDto> getSearchSliders(String keyword);

    List<SliderDto> getAllSliders();

    SliderDto getSliderById(Long id);

    boolean createSlider(SliderCreateDto sliderCreateDto);

    boolean updateSlider(Long id, SliderUpdateDto sliderUpdateDto);

    void deleteSlider(Long id);

    Long getTotalCount();
}
