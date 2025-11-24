# ===========================
# Stage 1 — Build com Gradle
# ===========================
FROM gradle:8.8-jdk21 AS builder

WORKDIR /app

# Copia todo o projeto para dentro do container
COPY . .

# Faz o build sem rodar os testes
RUN gradle clean build -x test


# ===========================
# Stage 2 — Runtime (o seu original)
# ===========================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o JAR gerado no stage 1
COPY --from=builder /app/build/libs/aprendendospring-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
