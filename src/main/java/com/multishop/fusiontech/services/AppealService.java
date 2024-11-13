package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.payloads.PaginationPayload;

import java.util.List;

public interface AppealService {

    boolean createAppeal(AppealDto appealDto);

    PaginationPayload<AppealDto> getSearchAppeals(String keyword, Integer pageNumber);

    PaginationPayload<AppealDto> getAllAppeals(Integer pageNumber);

    Long getTotalCount();
}
