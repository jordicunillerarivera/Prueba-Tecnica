package com.avoristech.hotelavailability.infrastructure.config.constants;

public class ApplicationConstants {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String OK = "200";

    // SearchController
    public static final String SEARCH_CONTROLLER_SUMMARY = "Crea una busqueda de disponibilidad de hotel";
    public static final String SEARCH_CONTROLLER_DESCRIPTION = "ID de la busqueda generada";

    // CountController
    public static final String COUNT_CONTROLLER_SUMMARY = "Cuenta busquedas similares a la original";
    public static final String COUNT_CONTROLLER_DESCRIPTION = "Detalle de busqueda y count";

    // BBDD
    public static final String COLLECTION_SEARCHES = "searches";



    private ApplicationConstants(){}
}
