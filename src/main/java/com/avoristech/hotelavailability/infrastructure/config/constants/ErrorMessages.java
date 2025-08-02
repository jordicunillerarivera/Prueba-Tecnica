package com.avoristech.hotelavailability.infrastructure.config.constants;

public class ErrorMessages {
    public static final String DATE_FORMAT = "Formato debe ser DD/MM/YYYY";
    public static final String HOTELID_NULL = "hotelId no puede ser null";
    public static final String SEARCHID_NULL = "searchId no puede ser null";
    public static final String PERIOD_NULL = "period no puede ser null";
    public static final String AGES_NULL = "ages no puede ser null";
    public static final String AGES_NEGATIVE = "Una o varias edades son negativas";
    public static final String CHECKIN_NULL = "checkin no puede ser null";
    public static final String CHECKOUT_NULL = "checkout no puede ser null";

    public static final String CHECKIN_OLDER_CHECKOUT = "checkIn debe se anterior a checkOut";

    public static final String HOTELID_EMPTY = "hotelId no puede estar vacio";
    public static final String AGES_EMPTY = "ages no puede estar vacio";

    public static final String SEARCHID_NOT_FOUND = "SearchId no encontrado: ";

    private ErrorMessages(){}
}
