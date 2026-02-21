# Sistema de Reservas de Salones

Un sistema de gestión de reservas de salones universitarios desarrollado en Java. Permite registrar salones, profesores y crear reservas con validaciones de disponibilidad y horarios.

## Características

- **Registrar Salones**: Añade nuevos salones con código, capacidad y ubicación
- **Registrar Profesores**: Gestiona profesores con ID, nombre y correo
- **Crear Reservas**: Crea reservas de salones con validaciones de:
  - Formato de fecha (YYYY-MM-DD)
  - Duración máxima de 3 horas
  - Capacidad del salón
  - Disponibilidad del salón y profesor
- **Cancelar Reservas**: Permite cancelar reservas existentes
- **Listar Reservas**: Muestra todas las reservas para una fecha específica
- **Mostrar Salones Disponibles**: Lista los salones disponibles para un rango de hora

## Estructura del Proyecto

```
PreQuiz01-Estructuras/
├── src/
│   ├── Main.java                    # Punto de entrada y menú principal
│   ├── classes/
│   │   ├── SistemaReservas.java     # Lógica principal del sistema
│   │   ├── Reserva.java             # Modelo de reserva
│   │   ├── Salon.java               # Modelo de salón
│   │   ├── Profesor.java            # Modelo de profesor
│   │   └── IO.java                  # Utilidad para entrada/salida
│   └── exceptions/
│       ├── FechaInvalidaException.java
│       ├── CapacidadInvalidaException.java
│       ├── HorarioInvalidoException.java
│       ├── ProfesorDuplicadoException.java
│       ├── ProfesorNoExisteException.java
│       ├── ReservaNoExisteException.java
│       ├── ReservaSolapadaException.java
│       ├── SalonDuplicadoException.java
│       ├── SalonNoExisteException.java
│       └── CupoMaximoException.java
```

## Requisitos

- Java 8 o superior
- Compilador javac

## Compilación y Ejecución

### Compilar
```bash
javac -d bin src/**/*.java
```

### Ejecutar
```bash
java -cp bin Main
```

## Uso

Al iniciar el programa, se cargarán automáticamente datos de ejemplo (4 salones, 4 profesores y 5 reservas). Luego verá un menú interactivo:

```
========== SISTEMA DE RESERVAS ==========
1. Registrar Salon
2. Registrar Profesor
3. Crear Reserva
4. Cancelar Reserva
5. Listar Reservas por Fecha
6. Mostrar Salones Disponibles
0. Salir
=========================================
```

### Ejemplo de Flujo

1. **Registrar un Salón**
   - Ingrese código: C301
   - Ingrese capacidad: 40
   - Ingrese ubicación: Edificio C - Piso 3

2. **Crear una Reserva**
   - Ingrese fecha: 2026-02-25
   - Ingrese hora inicio: 9
   - Ingrese hora fin: 11
   - Ingrese asistentes: 35
   - Seleccione salón disponible: C301
   - Seleccione profesor disponible: P001

## Validaciones

- **Fecha**: Debe estar en formato YYYY-MM-DD y no puede ser en el pasado
- **Duración**: Las reservas pueden durar entre 1 y 3 horas
- **Capacidad**: El número de asistentes no puede exceder la capacidad del salón
- **Disponibilidad**: Los salones y profesores no pueden tener reservas solapadas
- **Duplicados**: No se permiten códigos de salón o correos de profesor duplicados

## Datos de Ejemplo

El sistema carga automáticamente:

### Salones
- A101 (30 personas) - Edificio A, Piso 1
- A102 (25 personas) - Edificio A, Piso 1
- B201 (40 personas) - Edificio B, Piso 2
- B202 (35 personas) - Edificio B, Piso 2

### Profesores
- P001: Dr. Juan Perez
- P002: Dra. Maria Garcia
- P003: Dr. Carlos Lopez
- P004: Dra. Ana Rodriguez

### Reservas Iniciales
- 2026-02-21 09:00-11:00 A101 P001 (20 asistentes)
- 2026-02-21 12:00-14:00 B201 P002 (25 asistentes)
- 2026-02-22 10:00-12:00 A102 P003 (20 asistentes)
- 2026-02-22 14:00-16:00 B202 P004 (30 asistentes)
- 2026-02-23 11:00-13:00 A101 P001 (22 asistentes)

## Clases Principales

### SistemaReservas
Gestiona todas las operaciones del sistema:
- Registro de salones y profesores
- Creación y cancelación de reservas
- Búsquedas y validaciones
- Listados y disponibilidad

### Reserva
Representa una reserva con:
- ID único (auto-incrementado)
- Fecha en formato YYYY-MM-DD
- Horario (inicio y fin)
- Número de asistentes
- Referencias a salón y profesor

### Salon
Almacena información del salón:
- Código único
- Capacidad
- Ubicación

### Profesor
Almacena datos del profesor:
- ID único
- Nombre
- Correo electrónico

## Excepciones Personalizadas

El sistema lanza excepciones específicas para cada tipo de error:
- `FechaInvalidaException`: Fecha con formato o valor incorrecto
- `CapacidadInvalidaException`: Problemas con la capacidad o asistentes
- `ProfesorDuplicadoException`: Intento de registrar profesor duplicado
- `SalonDuplicadoException`: Intento de registrar salón duplicado
- `ReservaSolapadaException`: Conflicto de horario en reservas
- Y más...

## Notas

- El sistema usa arreglos estáticos con límites predefinidos (10 salones, 10 profesores, 50 reservas)
- Las reservas se almacenan en memoria durante la ejecución
- No hay persistencia en base de datos (los datos se pierden al cerrar)
- La fecha actual del sistema es 2026-02-21

## Autor

Samuel Giraldo && Alejandro Urrego 

Proyecto de práctica de estructura de datos en Java.

