package com.avoristech.hotelavailability.domain.model;

import java.util.Objects;

public record SearchCount (
    String searchId,
    Search search,
    long count
) {
    public SearchCount(String searchId, Search search, long count) {
        this.searchId = Objects.requireNonNull(searchId);
        this.search = Objects.requireNonNull(search);
        this.count = count;
    }
}
