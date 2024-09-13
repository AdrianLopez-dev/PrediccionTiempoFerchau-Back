# Usa la imagen base de JDK 22
FROM openjdk:22-jdk-alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado en la carpeta de destino
COPY target/backend-api-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 del contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]