# ---------- Etapa 1: compilación ----------
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

COPY src/ src/
RUN ./mvnw -q package -DskipTests

# ---------- Etapa 2: ejecución ----------
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --no-create-home appuser

COPY --from=build /workspace/target/*.jar app.jar

USER appuser
EXPOSE 8065

HEALTHCHECK --interval=15s --timeout=3s --start-period=40s --retries=5 \
  CMD curl -fsS http://localhost:8065/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]