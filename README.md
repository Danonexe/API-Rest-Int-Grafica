# Documentación del Proyecto Gestión de Tareas

## Descripción General
Este proyecto implementa una API REST para gestionar tareas personales con autenticación mediante JWT. Permite a los usuarios registrarse, iniciar sesión, y administrar tareas (crear, visualizar, marcar como completadas y eliminar). El sistema distingue entre usuarios regulares (USER) y administradores (ADMIN), con diferentes niveles de acceso a la funcionalidad.

## Análisis de utilizar programación orientada a componentes y una base de datos no relacional
Ventajas de POC: Reutilización de código, modularidad, escalabilidad y mejor mantenimiento.

Ventajas de MongoDB: Esquema flexible, escalabilidad, rapidez en grandes volúmenes de datos y manejo de datos semiestructurados.

## Estructura de la Base de Datos

### Colección: Usuarios
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | ObjectId | Identificador único del usuario |
| username | String | Nombre de usuario único |
| password | String | Contraseña del usuario |
| direccion | Objeto | Información de dirección (subdocumento) |
| roles | String | Rol del usuario (USER o ADMIN) |

#### Subdocumento: Dirección
| Campo | Tipo | Descripción |
|-------|------|-------------|
| municipio | String | Municipio de residencia |
| provincia | String | Provincia de residencia |
| calle | String | Nombre de la calle |
| numero | String | Número de la dirección |

### Colección: Tareas
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | ObjectId | Identificador único de la tarea |
| autor | String | Usuario que creó la tarea |
| objetivo | String | Descripción de la tarea |
| fecha | Date | Fecha de creación/vencimiento |

## Endpoints de la API

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/usuarios/login` | Autenticación de usuarios |
| POST | `/api/usuarios/register` | Registro de nuevos usuarios |
| GET | `/api/tareas` | Obtener lista de tareas |
| POST | `/api/tareas` | Crear nueva tarea |
| PUT | `/api/tareas/{id}/completar` | Marcar tarea como completada |
| PUT | `/api/tareas/{id}/descompletar` | Desmarcar tarea como completada |
| DELETE | `/api/tareas/{id}` | Eliminar tarea |

## Lógica de Negocio
La aplicación de gestión de tareas se basa en la gestión de usuarios con dos roles:

- **USER**: Pueden gestionar solo sus propias tareas (ver, crear, marcar como hechas y eliminar)
- **ADMIN**: Tienen control total sobre todas las tareas, pudiendo verlas, eliminarlas y crear tareas para cualquier usuario

Todos los usuarios pueden registrarse e iniciar sesión, independientemente de su rol.

## Manejo de Excepciones

### BadRequestException (400)
- Campos obligatorios vacíos
- Contraseñas que no coinciden
- Roles inválidos
- Provincia o municipio no existentes
- Tarea no encontrada

### UnauthorizedException (401)
- Credenciales inválidas al iniciar sesión
- Intentar crear/modificar/eliminar tareas sin permisos
- Token JWT inválido o expirado

### ConflictException (409)
- Intentar registrar un usuario con nombre de usuario ya existente

## Pruebas
[Consulta el documento de pruebas](./Pruebas.pdf)
