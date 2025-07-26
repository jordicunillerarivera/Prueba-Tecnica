package com.avoristech.hotelavailability.adapters.rest;

import com.avoristech.hotelavailability.adapters.rest.dto.CountResponseDTO;
import com.avoristech.hotelavailability.adapters.rest.dto.SearchRequestDTO;
import com.avoristech.hotelavailability.application.port.in.CountUseCase;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchCount;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import com.avoristech.hotelavailability.infrastructure.config.constants.ApiEndpoints;
import com.avoristech.hotelavailability.infrastructure.config.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoints.COUNT)
@Tag(name = ApplicationConstants.COUNT_CONTROLLER_DOC_NAME,
        description = ApplicationConstants.COUNT_CONTROLLER_DOC_DESCRIPTION)
public class CountController {
    private final CountUseCase countUseCase;

    public CountController(CountUseCase countUseCase) {
        this.countUseCase = countUseCase;
    }

    @Operation(summary = ApplicationConstants.COUNT_CONTROLLER_SUMMARY)
    @ApiResponse(responseCode = ApplicationConstants.OK, description = ApplicationConstants.COUNT_CONTROLLER_DESCRIPTION)
    @GetMapping
    public CountResponseDTO getCount(@RequestParam String searchId) {
        SearchCount result = countUseCase.countSearch(searchId);

        // mapeo dominio a DTO
        Search search = result.search();
        SearchRequestDTO dto = new SearchRequestDTO(
                search.getHotelId().value(),
                search.getPeriod().getCheckIn().format(SearchPeriod.FORMATTER),
                search.getPeriod().getCheckOut().format(SearchPeriod.FORMATTER),
                search.getAges()
        );

        return new CountResponseDTO(
                result.searchId(),
                dto,
                result.count()
        );
    }
}
