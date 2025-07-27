FROM eclipse-temurin:24-jdk AS build
WORKDIR /workspace

# 1. Copia solo los archivos necesarios para cachear dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. Descarga dependencias (caché eficiente)
RUN ./mvnw dependency:go-offline

# 3. Copia el código fuente y compila
COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]