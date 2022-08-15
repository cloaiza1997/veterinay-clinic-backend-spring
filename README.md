# veterinay-clinic-backend-spring

Backend que expone un API REST para el CRUD de una clínica veterinaria.

Desarrollado por [Cristian Loaiza](https://cloaiza1997.github.io/CristianLoaiza/).

---

## API

[http://44.201.226.225:8080/api/veterinary_clinic/](http://44.201.226.225:8080/api/veterinary_clinic/)

## Documentación

[http://44.201.226.225:8080/api/veterinary_clinic/swagger-ui/index.html](http://44.201.226.225:8080/api/veterinary_clinic/swagger-ui/index.html)

---

## Ejecución

- Limpiar compilado

  ```
  ./gradlew clean
  ```

- Compilar

  ```
  ./gradlew build
  ```

- Ubicación del compilado

  ```
  build\libs\veterinary.clinic-0.0.1-SNAPSHOT.jar
  ```

- Ejecutar compilado

  ```
  java -jar veterinary.clinic-0.0.1-SNAPSHOT.jar
  ```

---

## Consideraciones

- Realiza validaciones del lado de back de los formularios.
- Cuenta con migraciones.
- Endpoints documentados.

---

## Tecnologías utilizadas

- Spring Boot
- PostgreSQL 13

## Librerías

- JPA
- Lombok
- Thymeleaf
- Flywaydb

## Documentación

- Swagger
