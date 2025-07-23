package com.avoristech.hotelavailability.domain.model;

public record SearchCount (
    String searchId,
    Search search,
    long count
) {}
