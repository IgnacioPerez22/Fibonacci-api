# Usa una imagen de Java con Maven preinstalado
FROM maven:3.8.7-eclipse-temurin-17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia los archivos del proyecto al contenedor
COPY . .

# Ejecuta el comando de Maven para compilar y empaquetar la aplicación
RUN mvn clean install

# Expone el puerto en el que la aplicación estará escuchando
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/fibonacci-api-0.0.1-SNAPSHOT.jar"]
