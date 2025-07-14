# 🌐 Translation Management API

![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=spring)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue?logo=mysql)
![Security](https://img.shields.io/badge/Security-JWT%20%7C%20Spring%20Security-red?logo=springsecurity)
![Last Commit](https://img.shields.io/github/last-commit/RodriLang/tennis-tournaments-API)
![License](https://img.shields.io/github/license/RodriLang/tennis-tournaments-API)

Una API RESTful desarrollada con Spring Boot para gestionar proyectos de traducción, traductores, lenguajes y flujos de trabajo relacionados. La aplicación permite a los administradores asignar proyectos a traductores, quienes pueden aceptarlos, completarlos y actualizar su estado.

---

## 🚀 Características

- Gestión de **usuarios**, **administradores**, y **perfiles**.
- Soporte para **autenticación y códigos de verificación**.
- Administración de **lenguajes** y **pares de lenguas**.
- Asignación y seguimiento de **proyectos de traducción**.
- Envío de **correos electrónicos** automatizados.
- Roles y permisos basados en perfiles.
- Integración con bases de datos y arquitectura modular.

---

## 🧩 Módulos principales

| Controlador               | Funcionalidad principal                                          |
|--------------------------|------------------------------------------------------------------|
| `AdminController`        | Gestión de usuarios administradores.                            |
| `AuthUserController`     | Registro e inicio de sesión de usuarios.                        |
| `AuthCodeController`     | Generación y validación de códigos de autenticación.            |
| `UserProfileController`  | Información del perfil del usuario.                             |
| `UserAdminController`    | Asignación de roles y administración de usuarios.               |
| `EmailController`        | Envío de correos electrónicos (por ejemplo, confirmación).      |
| `LanguageController`     | CRUD de lenguajes disponibles.                                  |
| `LanguagePairController` | Gestión de pares de idiomas para traducciones.                  |
| `ProjectController`      | Creación, asignación, aceptación y seguimiento de proyectos.    |

---

## 🔐 Autenticación

El sistema utiliza autenticación basada en tokens (JWT) y códigos de validación por correo para registro o recuperación de cuentas.

---

## 📦 Endpoints principales (ejemplo)

```http
POST /auth/register         # Registro de nuevo usuario
POST /auth/login            # Inicio de sesión
GET  /projects              # Listado de proyectos disponibles
POST /projects/assign       # Asignar proyecto a traductor
POST /projects/accept       # Aceptación del proyecto por parte del traductor
```
🛠️ Tecnologías

    Java 21

    Spring Boot

    Spring Security

    JPA / Hibernate

    JWT

    MySQL

    Maven

    Lombok

📂 Estructura del proyecto

```plaintext
src/
 └── main/
     └── java/
         └── com/yourcompany/translation/
             ├── controllers/
             ├── services/
             ├── models/
             ├── repositories/
             └── config/
```
🧪 Ejecución local

git clone https://github.com/RodriLang/mvp-translation-project.git
cd translation-api
./mvnw spring-boot:run

Por defecto, la aplicación se ejecuta en: http://localhost:8080
📝 Licencia

Este proyecto está licenciado bajo la MIT License.

📧 Contacto

Si tenés alguna duda, sugerencia o consulta, podés escribir a: rodrigolang@gmail.com