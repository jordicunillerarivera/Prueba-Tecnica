package com.avoristech.hotelavailability.infrastructure.repository;

import com.avoristech.hotelavailability.infrastructure.config.constants.ApplicationConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = ApplicationConstants.COLLECTION_SEARCHES)
public class SearchDocument {
    @Id
    private String searchId;
    private String hotelId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> ages;

    public SearchDocument() {}

    public SearchDocument(String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }
}
