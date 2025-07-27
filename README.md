# Hotel Availability Service

 - Aplicacion Spring Boot que expone una API REST para busquedas de disponibilidad hotelera.
 - Usa arquitectura hexagonal y se integra con Kafka y MongoDB.
 - Proyecto desarrollado como prueba tecnica.

---

## Requisitos

 - JDK 24
 - Maven 3.9+
 - Docker + Docker Compose

---

## Levantar el entorno

 - Descomprimir Avoris-Prueba-Tecnica.rar si no se ha hecho ya.
 - Dentro de la carpeta descomprimida, ejecutar "docker-compose up -d".
   - Asegurarse de que los puertos 27017(MongoDB) y 9092(Kafka) estan libres.

## Documentacion Swagger

 - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - Al testear el POST /search, comprobar las fechas, Swagger pone numeros random.
 - OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints disponibles

### POST /search:

 - Inicia una busqueda de disponibilidad hotelera.

Body de ejemplo:
{
  "hotelId": "hotel-123",
  "checkIn": "01/01/2025",
  "checkOut": "03/01/2025",
  "ages": [30, 28]
}

### GET /count/{searchId}

 - Devuelve la busqueda exacta y la cantidad de busquedas similares.

## Test y reporte de cobertura

 - En la carpeta raiz (carpeta descomprimida), ejecutar "./mvmw clean verify".
 - El informe se creara en "target/site/jacoco/index.html".