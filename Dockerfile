# Sử dụng image Java chính thức làm base image
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build vào container
COPY target/your-app-0.0.1-SNAPSHOT.jar app.jar

# Cấu hình cổng mà ứng dụng sẽ chạy (Render sẽ tự động gán PORT qua biến môi trường)
ENV PORT=8920
EXPOSE 8920

# Lệnh để chạy ứng dụng Spring Boot
CMD ["java", "-jar", "app.jar"]