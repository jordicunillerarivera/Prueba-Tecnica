package com.avoristech.hotelavailability.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SearchDocumentRepository extends MongoRepository<SearchDocument, String> {
    @Query("{ 'hotelId': ?0, 'checkIn': ?1, 'checkOut': ?2 }")
    List<SearchDocument> findByHotelIdAndCheckInAndCheckOut(String hotelId,
                                                            LocalDate checkIn,
                                                            LocalDate checkOut);
}
