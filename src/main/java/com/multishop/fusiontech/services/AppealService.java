package com.multishop.fusiontech.services;

import com.multishop.fusiontech.dtos.singledtos.AppealDto;
import com.multishop.fusiontech.payloads.PaginationPayload;

public interface AppealService {

    boolean placeAppeal(AppealDto appealDto);

    PaginationPayload<AppealDto> getAdminAppeals(Integer pageNumber);

}
