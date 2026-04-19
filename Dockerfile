# ===== STAGE 1: BUILD (Đặt tên là 'compiler' cho chắc, tránh trùng tên hệ thống) =====
FROM maven:3.9.9-eclipse-temurin-21 AS compiler
WORKDIR /app

# Copy pom.xml và tải trước dependencies để tận dụng cache
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy code và build file .jar
COPY src ./src
RUN mvn -B clean package -DskipTests

# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Cài đặt curl để Railway/Docker check sức khỏe app
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# QUAN TRỌNG: --from phải khớp với tên stage ở trên (compiler)
COPY --from=compiler /app/target/*.jar app.jar

# Khai báo port để note cho local, Railway sẽ dùng biến $PORT thực tế
EXPOSE 8081

# Healthcheck dùng biến môi trường PORT, fallback về 8081
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s \
  CMD curl -f http://localhost:${PORT:-8081}/optics/actuator/health || exit 1

# Chạy ứng dụng và ép nhận Port từ Railway
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]
