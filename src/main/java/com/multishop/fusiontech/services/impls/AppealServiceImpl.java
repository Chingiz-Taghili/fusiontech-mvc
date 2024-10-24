package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.models.Appeal;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.repositories.AppealRepository;
import com.multishop.fusiontech.services.AppealService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PaginationPayload<AppealDto> getAdminAppeals(Integer pageNumber) {

        pageNumber = pageNumber == null ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        Page<Appeal> appeals = appealRepository.findAll(pageable);

        List<AppealDto> result = appeals.getContent().stream().map(appeal -> modelMapper.map(appeal, AppealDto.class)).toList();

        PaginationPayload<AppealDto> paginationPayload = new PaginationPayload<>();
        paginationPayload.setTotalPage(appeals.getTotalPages());
        paginationPayload.setCurrentPage(pageNumber);
        paginationPayload.setData(result);

        return paginationPayload;
    }
}
