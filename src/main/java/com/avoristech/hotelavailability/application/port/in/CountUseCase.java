package com.avoristech.hotelavailability.application.port.in;


import com.avoristech.hotelavailability.domain.model.SearchCount;

public interface CountUseCase {
    /**
     * Obtiene el numero de busquedas similares a la original
     *
     * @param searchId el identificador de la busqueda original
     * @return objeto de dominio con el searchId, la busqueda y el count
     */
    SearchCount countSearch(String searchId);
}
