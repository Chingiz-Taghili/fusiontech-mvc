package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.models.Appeal;
import com.multishop.fusiontech.repositories.AppealRepository;
import com.multishop.fusiontech.services.AppealService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final ModelMapper modelMapper;

    public AppealServiceImpl(AppealRepository appealRepository, ModelMapper modelMapper) {
        this.appealRepository = appealRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean placeAppeal(AppealDto appealDto) {
        try {
            Appeal appeal = modelMapper.map(appealDto, Appeal.class);
            appealRepository.save(appeal);
            return true;
        } catch (Exception e) {
         return false;
        }
    }
}
