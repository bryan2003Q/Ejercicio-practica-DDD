# Guía de Estudio: Monolito Modular con DDD y Clean Architecture

Este documento explica la estructura profesional construida en el proyecto. Pasamos de tener un "Código Espagueti" (un único controlador que hacía todo) a una arquitectura altamente modular, escalable y fácil de probar.

## 1. Arquitectura General (Cliente-Servidor)
El proyecto está físicamente dividido en dos partes independientes:
- **`backend/`**: API REST construida con Java y Spring Boot. (Puerto 8080)
- **`frontend/`**: Aplicación web construida con React y Vite. (Puerto 5173)

Ambos proyectos se comunican mediante peticiones HTTP (JSON) sin conocer los detalles de implementación del otro.

---

## 2. Estructura del Backend (Clean Architecture)

Dentro del backend, implementamos el patrón de **Domain-Driven Design (DDD)**.
El código está dividido en **Módulos** (`library`, `users`, `notifications`). Cada módulo funciona casi como un microservicio interno y se subdivide en tres capas estrictas:

### Capa 1: `domain` (El Corazón)
**Regla de oro:** Esta capa NO sabe nada de Spring Boot, ni de bases de datos, ni de internet. Es Java puro.
- **Entidades (`User.java`, `Book.java`)**: Representan conceptos del mundo real. Tienen validaciones y lógica de negocio. *(Ej: `book.rent(userId)` verifica si el libro ya está prestado antes de cambiar el estado).*
- **Interfaces de Repositorio (`UserRepository.java`)**: Son contratos que dictan cómo guardar la información, pero NO cómo conectarse a la base de datos.

### Capa 2: `application` (Los Casos de Uso)
**Regla de oro:** Aquí se orquesta la lógica.
- **Servicios (`UserService.java`)**: Reciben peticiones, buscan entidades en el dominio, ejecutan la lógica de negocio y guardan los resultados.
- **DTOs (`UserDTO.java`)**: (Data Transfer Objects). Son objetos simples sin lógica que se usan para enviar datos al exterior sin exponer las entidades de dominio completas.

### Capa 3: `infrastructure` (El Mundo Real)
**Regla de oro:** Todo lo que interactúa con frameworks externos va aquí.
- **REST (`UserController.java`)**: Los endpoints de la API (`@GetMapping`, `@PostMapping`).
- **Persistencia (`UserJpaEntity.java`, `SpringDataUserRepository.java`)**: Las clases anotadas con `@Entity` que Hibernate entiende para guardar datos en la base de datos (H2).
- **Adaptadores (`UserRepositoryAdapter.java`)**: Traducen entre la interfaz pura del `domain` y el repositorio real de Spring Data.

---

## 3. Desacoplamiento (Eventos de Dominio)

**El Problema:** En el código espagueti, cuando se prestaba un libro, el método de libros llamaba directamente al método de notificaciones para enviar un mensaje. Esto creaba un **Acoplamiento Fuerte**. Si el sistema de notificaciones fallaba, todo el proceso de préstamo fallaba.

**La Solución Elegante:**
1. **El Evento:** Creamos la clase `BookRentedEvent.java` en la carpeta `shared/domain/events`. Esto representa un hecho que ya ocurrió ("Un libro fue prestado").
2. **El Grito:** En `LibraryService.java`, cuando se presta un libro con éxito, usamos `ApplicationEventPublisher` para "disparar" el evento al aire.
3. **El Oyente:** En `BookRentedEventListener.java` (módulo de notificaciones), hay un método con `@EventListener`. Este código se ejecuta automáticamente en el fondo (como un fantasma) cuando detecta el evento y crea la notificación.

Gracias a esto, **el módulo de Libros no sabe que el módulo de Notificaciones existe.** Podríamos borrar el módulo de Notificaciones entero mañana y los libros se seguirían prestando sin problemas.

---

## 4. Flujo de Ejecución (Paso a Paso)

Cuando haces clic en "Prestar" en la página de React, esto es lo que ocurre internamente:

1. **Frontend:** React hace un `fetch` a `POST /api/books/1/rent?userId=1`.
2. **Infrastructure (REST):** `BookController.java` recibe la petición web y se la pasa a la aplicación.
3. **Application:** `LibraryService.java` orquesta el caso de uso: pide buscar el libro, pide buscar al usuario.
4. **Domain:** La entidad `Book.java` ejecuta el método `.rent()` verificando sus reglas internas.
5. **Infrastructure (Persistencia):** El adaptador convierte el `Book` a `BookJpaEntity` y lo guarda en la base de datos.
6. **Application (Eventos):** `LibraryService.java` publica el `BookRentedEvent`.
7. **Infrastructure (Notificaciones):** El Listener atrapa el evento y crea la notificación de éxito.
8. **Frontend:** React recibe el "Ok" y te muestra un `alert()`.
