# Usa la imagen base de Java
FROM openjdk:22-jdk

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado en la carpeta de destino
COPY target/PrediccionTiempoFerchau-Back-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 del contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]