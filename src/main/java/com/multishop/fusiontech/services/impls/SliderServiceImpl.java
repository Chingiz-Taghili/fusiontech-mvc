package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.SliderHomeDto;
import com.multishop.fusiontech.models.Slider;
import com.multishop.fusiontech.repositories.SliderRepository;
import com.multishop.fusiontech.services.SliderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {

    private final SliderRepository sliderRepository;
    private final ModelMapper modelMapper;

    public SliderServiceImpl(SliderRepository sliderRepository, ModelMapper modelMapper) {
        this.sliderRepository = sliderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SliderHomeDto> getHomeSliders() {
        List<Slider> repoSliders = sliderRepository.findAll();
        List<SliderHomeDto> homeSliders = repoSliders.stream().map(slider -> modelMapper.map(slider, SliderHomeDto.class)).toList();
        return homeSliders;
    }
}
