# Proyecto API REST con interfaz

## Daniel Hernández Gómez

## Nombre del proyecto
El nombre de mi proyecto va a ser **Gestión de Tareas**.

## Descripción

## Descripción de los documentos
En mi proyecto la Base de datos va a tener los siguientes documentos:

### Usuarios:
- **_id**: ObjectId
- **username**: String
- **password**: String
- **direccion**: Direccion
- **roles**: String

#### Subdocumento Direccion:
- **municipio**: String
- **provincia**: String
- **calle**: String
- **numero**: String

### Tareas:
- **_id**: ObjectId
- **autor**: String
- **objetivo**: String
- **fecha**: Date

## Endpoints

- **POST**: `/API/usuarios/register` - Registro del usuario  
- **POST**: `/API/usuarios/login` - Loguearse como usuario  
- **GET**: `/API/tareas` - Ver las tareas  
- **PUT**: `/API/tareas/{id}/done` - Poner una tarea como completada  
- **DELETE**: `/API/tareas/{id}` - Eliminar una tarea  
- **POST**: `/API/tareas` - Crear una tarea  

## Lógica de negocio
La lógica de negocio de esta aplicación de gestión de tareas se basa en la gestión de usuarios con dos roles: `USER` y `ADMIN`.

- Todos pueden registrarse e iniciar sesión.
- Usuarios con rol `USER` pueden gestionar solo sus propias tareas (ver, crear, marcar como hechas y eliminar).
- Usuarios con rol `ADMIN` tienen control total sobre todas las tareas, pudiendo verlas, eliminarlas y crear tareas para cualquier usuario.

## Excepciones

