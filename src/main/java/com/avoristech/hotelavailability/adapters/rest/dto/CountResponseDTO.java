package com.avoristech.hotelavailability.adapters.rest.dto;

public record CountResponseDTO(
        String searchId,
        SearchRequestDTO search,
        long count
) {
}
