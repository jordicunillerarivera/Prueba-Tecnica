package com.avoristech.hotelavailability.application.port.out;

import com.avoristech.hotelavailability.domain.model.Search;

public interface SearchMessagingPort {
    /**
     * Envia el objeto Search al topic de Kafka
     *
     * @param search objeto de dominio con hotelId, periodo y edades
     */
    void send(Search search);
}
