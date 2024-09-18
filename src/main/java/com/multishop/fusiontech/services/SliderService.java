package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.singledtos.SliderHomeDto;

import java.util.List;

public interface SliderService {

    List<SliderHomeDto> getHomeSliders();
}
