import classes.SistemaReservas;
import exceptions.*;
import java.util.Scanner;

public class Main {
    private static SistemaReservas sistema;
    private static Scanner scanner;

    static void main() {
        scanner = new Scanner(System.in);
        sistema = new SistemaReservas(10, 10, 50);

        cargarDatosEjemplo();

        boolean activo = true;
        while (activo) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarSalon();
                    break;
                case 2:
                    registrarProfesor();
                    break;
                case 3:
                    crearReserva();
                    break;
                case 4:
                    cancelarReserva();
                    break;
                case 5:
                    listarReservasPorFecha();
                    break;
                case 6:
                    mostrarSalonesDisponibles();
                    break;
                case 0:
                    activo = false;
                    IO.println("Hasta luego!");
                    System.exit(1);
                default:
                    IO.println("Opcion no valida");
            }
        }
    }

    private static void mostrarMenu() {
        IO.println("\n========== SISTEMA DE RESERVAS ==========");
        IO.println("1. Registrar Salon");
        IO.println("2. Registrar Profesor");
        IO.println("3. Crear Reserva");
        IO.println("4. Cancelar Reserva");
        IO.println("5. Listar Reservas por Fecha");
        IO.println("6. Mostrar Salones Disponibles");
        IO.println("0. Salir");
        IO.println("=========================================");
        IO.println("Seleccione una opcion: ");
    }

    private static void registrarSalon() {
        IO.println("--- Registrar Nuevo Salon ---");

        scanner.nextLine();

        IO.println("Codigo del salon: ");
        String codigo = scanner.nextLine();

        IO.println("Capacidad: ");
        int capacidad = leerEntero();

        IO.println("Ubicacion: ");
        String ubicacion = scanner.nextLine();

        try {
            sistema.registrarSalon(codigo, capacidad, ubicacion);
            IO.println("Salon registrado");
        } catch (SalonDuplicadoException e) {
            IO.println("Error: " + e.getMessage());
        } catch (CupoMaximoException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private static void registrarProfesor() {
        IO.println("--- Registrar Nuevo Profesor ---");

        scanner.nextLine();

        IO.println("ID del profesor: ");
        String id = scanner.nextLine();

        IO.println("Nombre: ");
        String nombre = scanner.nextLine();

        IO.println("Correo: ");
        String correo = scanner.nextLine();

        try {
            sistema.registrarProfesor(id, nombre, correo);
            IO.println("Profesor registrado");
        } catch (ProfesorDuplicadoException e) {
            IO.println("Error: " + e.getMessage());
        } catch (CupoMaximoException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private static void crearReserva() {
        IO.println("--- Crear Nueva Reserva ---");

        IO.println("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        try {
            sistema.fechaValida(fecha);
        } catch (FechaInvalidaException e) {
            IO.println("Error: " + e.getMessage());
            return;
        }

        IO.println("Hora inicio (7 - 20): ");
        int horaInicio = leerEntero();

        IO.println("Hora fin (7 - 20): ");
        int horaFin = leerEntero();

        IO.println("Numero de asistentes: ");
        int asistentes = leerEntero();

        IO.println("Salones disponibles:");
        sistema.mostrarSalonesDisponibles(fecha, horaInicio, horaFin);

        IO.println("Codigo del salon: ");
        String codigoSalon = scanner.nextLine();

        IO.println("Profesores disponibles:");
        sistema.listarProfesores();

        IO.println("ID del profesor: ");
        String idProfesor = scanner.nextLine();

        try {
            int idReserva = sistema.crearReserva(fecha, horaInicio, horaFin, asistentes, codigoSalon, idProfesor);
            if (idReserva >= 0) {
                IO.println("Reserva creada con ID: " + idReserva);
            }
        } catch (ProfesorNoExisteException e) {
            IO.println("Error: " + e.getMessage());
        } catch (SalonNoExisteException e) {
            IO.println("Error: " + e.getMessage());
        } catch (ReservaSolapadaException e) {
            IO.println("Error: " + e.getMessage());
        } catch (CapacidadInvalidaException e) {
            IO.println("Error: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        IO.println("--- Cancelar Reserva ---");

        listarReservasPorFecha();
        scanner.nextLine();

        IO.println("ID de la reserva a cancelar: ");
        int idReserva = leerEntero();

        try {
            sistema.cancelarReserva(idReserva);
            IO.println("Reserva cancelada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private static void listarReservasPorFecha() {
        IO.println("--- Listar Reservas por Fecha ---");

        IO.println("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        try {
            sistema.fechaValida(fecha);
        } catch (FechaInvalidaException e) {
            IO.println("Error: " + e.getMessage());
            return;
        }

        IO.println("Reservas para " + fecha + ":");
        sistema.listarReservasPorFecha(fecha);
    }

    private static void mostrarSalonesDisponibles() {
        IO.println("--- Mostrar Salones Disponibles ---");

        IO.println("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        try {
            sistema.fechaValida(fecha);
        } catch (FechaInvalidaException e) {
            IO.println("Error: " + e.getMessage());
            return;
        }

        IO.println("Hora inicio (7-20): ");
        int horaInicio = leerEntero();

        IO.println("Hora fin (7-20): ");
        int horaFin = leerEntero();

        IO.println("Salones disponibles:");
        sistema.mostrarSalonesDisponibles(fecha, horaInicio, horaFin);
    }

    private static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            IO.println("Numero invalido");
            return -1;
        }
    }

    private static void cargarDatosEjemplo() {
        IO.println("CARGANDO DATOS DE EJEMPLO\n");

        try {
            sistema.registrarSalon("A101", 30, "Edificio A - Piso 1");
            IO.println("Salon A101 registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarSalon("A102", 25, "Edificio A - Piso 1");
            IO.println("Salon A102 registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarSalon("B201", 40, "Edificio B - Piso 2");
            IO.println("Salon B201 registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarSalon("B202", 35, "Edificio B - Piso 2");
            IO.println("Salon B202 registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarProfesor("P001", "Dr. Juan Perez", "juan.perez@universidad.edu");
            IO.println("Profesor Juan Perez registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarProfesor("P002", "Dra. Maria Garcia", "maria.garcia@universidad.edu");
            IO.println("Profesor Maria Garcia registrada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarProfesor("P003", "Dr. Carlos Lopez", "carlos.lopez@universidad.edu");
            IO.println("Profesor Carlos Lopez registrado");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.registrarProfesor("P004", "Dra. Ana Rodriguez", "ana.rodriguez@universidad.edu");
            IO.println("Profesor Ana Rodriguez registrada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.crearReserva("2026-02-21", 9, 11, 20, "A101", "P001");
            IO.println("Reserva 1 creada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.crearReserva("2026-02-21", 12, 14, 25, "B201", "P002");
            IO.println("Reserva 2 creada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.crearReserva("2026-02-22", 10, 12, 20, "A102", "P003");
            IO.println("Reserva 3 creada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.crearReserva("2026-02-22", 14, 16, 30, "B202", "P004");
            IO.println("Reserva 4 creada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        try {
            sistema.crearReserva("2026-02-23", 11, 13, 22, "A101", "P001");
            IO.println("Reserva 5 creada");
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }

        IO.println("\nDatos cargados\n");
    }
}
