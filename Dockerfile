# ... (Phần Build Stage giữ nguyên)

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Cài curl vì bản JRE mặc định không có
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar app.jar

# Sử dụng biến môi trường cho Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s \
  CMD curl -f http://localhost:${PORT:-8081}/optics/actuator/health || exit 1

# Dùng ENTRYPOINT để truyền tham số Port trực tiếp vào Java
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]
