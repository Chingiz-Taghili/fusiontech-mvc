package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.slider.SliderCreateDto;
import com.multishop.fusiontech.dtos.slider.SliderHomeDto;
import com.multishop.fusiontech.dtos.slider.SliderUpdateDto;
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

    @Override
    public SliderHomeDto getSliderById(Long id) {
        Slider repoSlider = sliderRepository.findById(id).orElseThrow();
        SliderHomeDto slider = modelMapper.map(repoSlider, SliderHomeDto.class);
        return slider;
    }

    @Override
    public boolean createSlider(SliderCreateDto sliderCreateDto) {
        try {
            Slider slider = modelMapper.map(sliderCreateDto, Slider.class);
            sliderRepository.save(slider);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSlider(Long id, SliderUpdateDto sliderUpdateDto) {
        try {
            Slider findSlider = sliderRepository.findById(id).orElseThrow();
            findSlider.setTitle(sliderUpdateDto.getTitle());
            findSlider.setDescription(sliderUpdateDto.getDescription());
            findSlider.setImage(sliderUpdateDto.getImage());
            findSlider.setActive(sliderUpdateDto.isActive());
            sliderRepository.save(findSlider);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteSlider(Long id) {
        sliderRepository.deleteById(id);
    }
}
