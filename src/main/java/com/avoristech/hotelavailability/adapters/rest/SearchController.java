package com.avoristech.hotelavailability.adapters.rest;

import com.avoristech.hotelavailability.adapters.rest.dto.SearchRequestDTO;
import com.avoristech.hotelavailability.adapters.rest.dto.SearchResponseDTO;
import com.avoristech.hotelavailability.application.port.in.SearchUseCase;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchUseCase searchUseCase;

    public SearchController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @Operation(summary = "Crea una busqueda de disponibilidad de hotel")
    @ApiResponse(responseCode = "200", description = "ID de la busqueda generada")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SearchResponseDTO createSearch(@Valid @RequestBody SearchRequestDTO dto) {
        // Mapear DTO al dominio
        HotelId hotelId = new HotelId(dto.hotelId());
        SearchPeriod period = new SearchPeriod(dto.checkIn(), dto.checkOut());
        Search search = Search.of(hotelId, period, dto.ages());

        // Invocar caso de uso
        String searchId = searchUseCase.createSearch(search);

        return new SearchResponseDTO(searchId);
    }
}
