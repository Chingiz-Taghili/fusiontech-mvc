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
    public boolean createAppeal(AppealDto appealDto) {
        try {
            Appeal appeal = modelMapper.map(appealDto, Appeal.class);
            appealRepository.save(appeal);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PaginationPayload<AppealDto> getSearchAppeals(String keyword, Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        Page<Appeal> repoAppeals = appealRepository.findByKeywordInColumnsIgnoreCase(keyword, pageable);

        List<AppealDto> appeals = repoAppeals.getContent().stream().map(appeal -> modelMapper.map(appeal, AppealDto.class)).toList();
        PaginationPayload<AppealDto> paginationAppeals = new PaginationPayload<>(
                repoAppeals.getTotalPages(), pageNumber, appeals);

        return paginationAppeals;
    }

    @Override
    public PaginationPayload<AppealDto> getAllAppeals(Integer pageNumber) {

        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 15, Sort.by("id"));
        Page<Appeal> repoAppeals = appealRepository.findAll(pageable);

        List<AppealDto> appeals = repoAppeals.getContent().stream().map(appeal -> modelMapper.map(appeal, AppealDto.class)).toList();

        PaginationPayload<AppealDto> paginationAppeals = new PaginationPayload<>
                (repoAppeals.getTotalPages(), pageNumber, appeals);

        return paginationAppeals;
    }

    @Override
    public Long getTotalCount() {
        return appealRepository.count();
    }
}
