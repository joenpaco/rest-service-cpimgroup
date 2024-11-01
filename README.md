# User Registration API

## Descripción

Esta aplicación expone una API RESTful en Spring Boot para la creación, actualización, eliminación y obtención de usuarios. Los datos de los usuarios se almacenan en una base de datos en memoria (H2) y cada usuario cuenta con un token de autenticación (JWT).

## Ubicación
La API estará disponible en http://localhost:8080/api/users

## EndPoints
- **POST /api/users/register:** Registrar un usuario
- **GET /api/users:** Obtener todos los usuarios
- **PUT /api/users/{id}:** Actualizar usuario por id
- **DELETE /api/users/{id}:** Eliminar usuario por id

## Documentación de la API (Swagger)
Para visualizar y probar los endpoints de la API, acceder a la siguiente URL: http://localhost:8080/swagger-ui.html.


## Requisitos

- **Java**: 8 o superior
- **Maven**: 3.6 o superior
- **IDE**: Recomendado (IntelliJ IDEA, Eclipse o Visual Studio Code)

## Tecnologías

- **Spring Boot**: Framework para construir la API REST
- **H2 Database**: Base de datos en memoria
- **JWT**: Token de autenticación
- **JUnit y Mockito**: Pruebas unitarias y simulación
- **Jakarta Bean Validation**: Validación de los datos de usuario
- **Swagger**: Para visualizar y probar los endpoints de la API

## Instalación y Configuración

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/joenpaco/rest-service-cpimgroup.git
    ```

2. Configurar las propiedades en `application.properties`:

   En el archivo `src/main/resources/application.properties`, configura los valores necesarios:

    ```properties
    # JWT Secret Key
    jwt.token.secret=MI_CLAVE_SECRETA_EN_BASE64

    # Validación de usuario
    validation.email.regex=^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$
    validation.email.message=Correo no válido
    validation.password.regex=^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$
    validation.password.message=La contraseña debe tener al menos 8 caracteres, incluyendo una letra mayúscula, una minúscula, un número y un carácter especial
   
    # Otras validaciones
    validation.name.message=El nombre no puede estar vacío
    validation.active.message=true
   
    # Configuración de H2
    spring.datasource.url=jdbc:h2:mem:cpimgroupdb
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=org.h2.Driver
    spring.h2.console.enabled=true
    spring.jpa.hibernate.ddl-auto=create-drop
    logging.level.org.hibernate.SQL=debug
    ```

3. Compilar el proyecto:
    ```bash
    mvn clean install
    ```

## Ejecución de la Aplicación

Para ejecutar la aplicación, usa el siguiente comando:

```bash
mvn spring-boot:run
