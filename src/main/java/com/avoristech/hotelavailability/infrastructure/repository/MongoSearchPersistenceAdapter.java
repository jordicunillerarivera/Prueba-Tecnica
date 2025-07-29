package com.avoristech.hotelavailability.infrastructure.repository;

import com.avoristech.hotelavailability.domain.port.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoSearchPersistenceAdapter implements SearchPersistencePort {
    private final SearchDocumentRepository repository;

    public MongoSearchPersistenceAdapter(SearchDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Search search) {
        SearchPeriod period = search.getPeriod();
        SearchDocument doc = new SearchDocument(
                search.getSearchId(),
                search.getHotelId().value(),
                period.getCheckIn(),
                period.getCheckOut(),
                search.getAges()
        );
        repository.save(doc);
    }

    @Override
    public List<Search> findSimilar(Search search) {
        SearchPeriod period = search.getPeriod();
        List<SearchDocument> docs = repository.findByHotelIdAndCheckInAndCheckOut(
                search.getHotelId().value(),
                period.getCheckIn(),
                period.getCheckOut()
        );

        return docs.stream()
                .map(d -> new Search(
                        d.getSearchId(),
                        new HotelId(d.getHotelId()),
                        new SearchPeriod(
                                d.getCheckIn().format(SearchPeriod.FORMATTER),
                                d.getCheckOut().format(SearchPeriod.FORMATTER)
                        ),
                        d.getAges()
                ))
                .filter(s -> s.equals(search))
                .toList();
    }

    @Override
    public Search findById(String searchId) {
        return repository.findById(searchId)
                .map(d -> new Search(
                        d.getSearchId(),
                        new HotelId(d.getHotelId()),
                        new SearchPeriod(
                                d.getCheckIn().format(SearchPeriod.FORMATTER),
                                d.getCheckOut().format(SearchPeriod.FORMATTER)
                        ),
                        d.getAges()
                ))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.SEARCHID_NOT_FOUND + searchId));
    }

}
