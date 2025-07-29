package com.avoristech.hotelavailability.application.service;

import com.avoristech.hotelavailability.application.port.in.CountUseCase;
import com.avoristech.hotelavailability.domain.port.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountService implements CountUseCase {
    private final SearchPersistencePort persistencePort;

    public CountService(SearchPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchCount countSearch(String searchId) {
        Search original = persistencePort.findById(searchId);
        long total = persistencePort.findSimilar(original).size();
        return new SearchCount(searchId, original, total);
    }
}
