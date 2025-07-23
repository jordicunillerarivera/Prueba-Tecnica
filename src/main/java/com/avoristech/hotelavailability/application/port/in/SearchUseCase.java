package com.avoristech.hotelavailability.application.port.in;

import com.avoristech.hotelavailability.domain.model.Search;

public interface SearchUseCase {
    /**
     * Procesa una nueva busqueda: genera searchId, envia a Kafka y persiste via consumidor
     *
     * @param search objeto de dominio con hotelId, periodo y edades
     * @return el searchId generado
     */
    String createSearch(Search search);
}
