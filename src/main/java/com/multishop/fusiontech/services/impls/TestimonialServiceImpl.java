package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.TestimonialDto;
import com.multishop.fusiontech.models.Testimonial;
import com.multishop.fusiontech.repositories.TestimonialRepository;
import com.multishop.fusiontech.services.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final ModelMapper modelMapper;

    public TestimonialServiceImpl(TestimonialRepository testimonialRepository, ModelMapper modelMapper) {
        this.testimonialRepository = testimonialRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TestimonialDto> getAllTestimonials() {
        List<Testimonial> repoTestimonials = testimonialRepository.findAll();
        List<TestimonialDto> testimonials = repoTestimonials.stream().map(t -> modelMapper.map(t, TestimonialDto.class)).toList();
        return testimonials;
    }
}
