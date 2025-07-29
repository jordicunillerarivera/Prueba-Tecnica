package com.avoristech.hotelavailability.infrastructure.adapters.messaging;

import java.util.List;

public record SearchMessageDTO(
        String searchId,
        String hotelId,
        String checkIn,
        String checkOut,
        List<Integer> ages
) {
}
