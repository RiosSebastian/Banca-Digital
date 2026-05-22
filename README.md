🏦 Digital Banking API – Spring Boot

Backend de una plataforma de banca digital desarrollada con Spring Boot, que implementa autenticación segura mediante JWT, gestión de usuarios con roles, cuentas bancarias y transacciones financieras.

El proyecto sigue buenas prácticas de arquitectura, uso de DTOs, mappers, manejo global de excepciones y seguridad stateless.

-------------------------------------------------------------------------
🚀 Tecnologías utilizadas

☕ Java 17+

🌱 Spring Boot

🔐 Spring Security + JWT

🗄️ Spring Data JPA / Hibernate

🐘 Base de datos relacional (MySQL / H2)

🧾 DTO + Mapper Pattern

🧪 Validaciones con Jakarta Validation

📦 Lombok

📖 Swagger / OpenAPI

🌍 CORS configurado para frontend React

------------------------------------------------------------------------
🔐 Seguridad

Autenticación basada en JWT

Autorización por roles:

ADMIN

USER

INVITED

Uso de @PreAuthorize para control de acceso

Sesiones STATELESS

Filtro personalizado JwtAuthenticationFilter

Contraseñas encriptadas con BCrypt

------------------------------------------------------------------------
👤 Gestión de Usuarios

Funcionalidades:

Crear usuario

Obtener usuario por ID

Listar usuarios (solo ADMIN)

Actualizar usuario

Eliminar usuario (solo ADMIN)

Roles asignados mediante UserEnum.

💳 Gestión de Cuentas

Creación automática de:

Alias

CBU

Balance inicial

Relación 1:1 entre Usuario y Cuenta

Eliminación y listado de cuentas

💸 Transacciones

Tipos de transacciones soportadas:

DEPOSITO

RETIRO

TRANSFERENCIA

Cada transacción queda registrada con:

Fecha

Monto

Tipo

Cuenta origen / destino
--------------------------------------------------------------
📦 Arquitectura del Proyecto
src/main/java
├── config          # Seguridad, JWT, CORS
├── controller      # Controladores REST
├── dto             # DTOs Request / Response
├── entity          # Entidades JPA
├── exception       # Excepciones personalizadas
├── mapper          # Conversión Entity ↔ DTO
├── repository      # Repositorios JPA
├── service         # Lógica de negocio
└── security        # Filtros y utilidades JWT

--------------------------------------------------------
⚠️ Manejo de Excepciones

Se implementa un GlobalExceptionHandler que captura:

Recurso no encontrado (404)

Datos inválidos (400)

Credenciales incorrectas (401)

Conflictos (409)

Errores internos (500)

Todas las respuestas siguen un formato estándar con timestamp y detalles.

------------------------------------------------------------
🧠 Próximas mejoras

🔄 Refresh Token

🍪 JWT en Cookies HttpOnly

📊 Historial de movimientos paginado

🧪 Tests unitarios y de integración

📱 Integración con frontend React
