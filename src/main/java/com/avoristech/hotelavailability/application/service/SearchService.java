package com.avoristech.hotelavailability.application.service;

import com.avoristech.hotelavailability.application.port.in.SearchUseCase;
import com.avoristech.hotelavailability.application.port.out.SearchMessagingPort;
import com.avoristech.hotelavailability.domain.model.Search;
import org.springframework.transaction.annotation.Transactional;

public class SearchService implements SearchUseCase {
    private final SearchMessagingPort messagingPort;

    public SearchService(SearchMessagingPort messagingPort) {
        this.messagingPort = messagingPort;
    }

    @Override
    @Transactional(readOnly = true)
    public String createSearch(Search search) {
        // Enviamos a kafka
        messagingPort.send(search);

        // Devolvemos el Id generado por dominio
        return search.getSearchId();
    }
}
