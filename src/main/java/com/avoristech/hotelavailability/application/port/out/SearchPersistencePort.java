package com.avoristech.hotelavailability.application.port.out;

import com.avoristech.hotelavailability.domain.model.Search;

import java.util.List;

public interface SearchPersistencePort {
    /**
     * Persiste una busqueda en la BBDD.
     *
     * @param search objeto de dominio con hotelId, periodo y edades
     */
    void save(Search search);

    /**
     * Busca todas las busquedas que coincidan con el mismo hotelId, periodo y conjunto de edades.
     *
     * @param search objeto de dominio con hotelId, periodo y edades
     * @return Devuelve una lista con Search similares
     */
    List<Search> findSimilar(Search search);

    /**
     * Recupera una busqueda por su ID
     *
     * @param searchId el identificador de la busqueda original
     * @return Devuelve un Search por identificador
     */
    Search findById(String searchId);
}
