package classes;

import exceptions.*;

import static java.util.Objects.isNull;

public class SistemaReservas {
    private static int counter = 0;
    private Salon[] salones;
    private int numeroSalones;
    private Profesor[] profesores;
    private int numeroProfesores;
    private Reserva[] reservas;
    private int numeroReservas;
    private int id;

    public SistemaReservas(int numeroSalones, int numeroProfesores, int numeroReservas) {
        this.numeroSalones = numeroSalones;
        salones = new Salon[numeroSalones];
        this.numeroProfesores = numeroProfesores;
        profesores = new Profesor[numeroProfesores];
        this.numeroReservas = numeroReservas;
        reservas = new Reserva[numeroReservas];
        id = counter++;
    }

    public void registrarSalon(String codigo, int capacidad, String ubicación) throws SalonDuplicadoException, CupoMaximoException {
        Salon s = new Salon(codigo,capacidad,ubicación);

        for (int i = 0; i < numeroSalones; i++) {
            if (!isNull(salones[i]) && salones[i].getCodigo().equals(codigo)) {
                throw new SalonDuplicadoException("El salon con ese código, ya existe haga otro");
            }
        }

        for (int i = 0; i < numeroSalones; i++) {
            if (isNull(salones[i])) {
                salones[i] = s;
                return;
            }
        }

        throw new CupoMaximoException("Cupo máximo de salones alcanzado perrita 😛");
    }

    public void registrarProfesor(String id, String nombre, String correo) throws ProfesorDuplicadoException,CupoMaximoException {

        Profesor p = new Profesor(id, nombre, correo);

        for (int i = 0; i < numeroProfesores; i++) {
            if (!isNull(profesores[i]) && profesores[i].getCorreo().equals(correo)) {
                    throw new ProfesorDuplicadoException("El profesor con ese correo ya existe");
            }
        }

        for (int i = 0; i <numeroProfesores; i++) {
            if (isNull(profesores[i])) {
                profesores[i] = p;
                return;
            }
        }

        throw new CupoMaximoException("Cupo máximo de profesores alcanzado perrita 😛");

    }

    public int crearReserva(String fecha, int horaInicio, int horaFin, int asistentes, String codigoSalon, String idProfesor) throws ProfesorNoExisteException, SalonNoExisteException, ReservaSolapadaException, CapacidadInvalidaException{

        Reserva r = new Reserva(fecha, horaInicio, horaFin, asistentes, buscarSalonPorCodigo(codigoSalon), buscarProfesorPorId(idProfesor));

        try {
            if (existeReservaSolapada(r)) {
                for (int i = 0; i < numeroReservas; i++) {
                    if (isNull(reservas[i])) {
                        reservas[i] = r;
                        return r.getIdReserva();
                    }
                }
                throw new CupoMaximoException("Cupo máximo de reservas alcanzado perrita 😛");
            }
        } catch (ProfesorNoExisteException | SalonNoExisteException | ReservaSolapadaException | CupoMaximoException | CapacidadInvalidaException e) {
            IO.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    public void cancelarReserva(int idReserva){

        if (buscarReservaPorId(idReserva) == null) {
            return;
        }

        for (int i = 0; i < numeroReservas; i++) {
            if (!isNull(reservas[i]) && reservas[i].getIdReserva() == idReserva) {
                reservas[i] = null;
                return;
            }
        }
    }

    public void listarReservasPorFecha(String fecha){
            for (int i = 0; i < numeroReservas; i++) {
                if (!isNull(reservas[i]) && reservas[i].getFecha().equals(fecha)) {
                    IO.println(reservas[i].toString());
                }
            }
    }

    public void mostrarSalonesDisponibles(String fecha, int horaInicio, int horaFin) {
        for (int i = 0; i < numeroSalones; i++) {
            if (!isNull(salones[i])) {

                boolean disponible = true;
                for (int j = 0; j < numeroReservas; j++) {
                    if (!isNull(reservas[j]) && reservas[j].getSalon().equals(salones[i]) && reservas[j].getFecha().equals(fecha) &&
                            reservas[j].getHoraInicio() < horaFin && horaInicio < reservas[j].getHoraFin()) {
                        disponible = false;
                        break;
                    }
                }
                if (disponible) {
                    IO.println(salones[i].toString());
                }
            }


        }
    }

    public Salon buscarSalonPorCodigo(String codigo) throws SalonNoExisteException {
        for (int i = 0; i < numeroSalones; i++) {
            if(!isNull(salones[i]) && salones[i].getCodigo().equals(codigo)) {
                return salones[i];
            }
        }
        throw new SalonNoExisteException("El salon con ese codígo no existe perro");
    }

    public Profesor buscarProfesorPorId(String Id) throws ProfesorNoExisteException {

        for (int i = 0; i < numeroProfesores; i++) {
            if (!isNull(profesores[i]) && profesores[i].getId().equals(Id)) {
                    return profesores[i];
            }
        }
        throw new ProfesorNoExisteException("Eje profejor no ejiste");
    }

    public boolean existeReservaSolapada(Reserva nueva) throws ReservaSolapadaException{

        try {
            for (int i = 0; i < numeroReservas; i++) {
                if (!isNull(reservas[i]) &&
                        (
                                reservas[i].equals(nueva) ||
                                        ( //Not id being equal
                                                reservas[i].getFecha().equals(nueva.getFecha()) &&
                                                        reservas[i].getAsistentes() == nueva.getAsistentes() &&
                                                        reservas[i].getHoraFin() == nueva.getHoraFin() &&
                                                        reservas[i].getHoraInicio() == nueva.getHoraInicio() &&
                                                        reservas[i].getProfesor().equals(nueva.getProfesor()) &&
                                                        reservas[i].getSalon().equals(nueva.getSalon())
                                        )
                                        ||
                                        ( // hora inicio or hora fin overlapping WITH the same salón
                                                reservas[i].getFecha().equals(nueva.getFecha()) &&
                                                        reservas[i].getHoraInicio() < nueva.getHoraFin() &&
                                                        nueva.getHoraInicio() < reservas[i].getHoraFin() &&
                                                        reservas[i].getSalon().equals(nueva.getSalon())
                                        )
                        )
                ) {
                    throw new ReservaSolapadaException("La reserva nueva cae dentro de otra reserva en el mismo horario");
                }
            }
            return true;
        } catch (ReservaSolapadaException e) {
            IO.println(e.getMessage());
            return false;
        }
    }

    public boolean fechaValida(String fecha) {

        String[] partes = fecha.split("-");

        if (partes.length != 3) {
            throw new FechaInvalidaException("La fecha debe tener el formato YYYY-MM-DD ??");
        }

        try {
            int año = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int dia = Integer.parseInt(partes[2]);

            if (año != 2026) {
                throw new FechaInvalidaException("El año debe ser 2026 ??");
            }

            if (mes < 1 || mes > 12) {
                throw new FechaInvalidaException("El mes debe estar entre 1 y 12");
            }

            if (dia < 1 || dia > 31) {
                throw new FechaInvalidaException("El día debe estar entre 1 y 31 ??");
            }

            return true;
        } catch (FechaInvalidaException e) {
            throw new FechaInvalidaException("La fecha debe contener números válidos ??");
        }


    }

    public Reserva buscarReservaPorId(int idReserva) throws ReservaNoExisteException {
        try {
            for (int i = 0; i < numeroReservas; i++) {
                if (!isNull(reservas[i]) && reservas[i].getIdReserva() == idReserva) {
                    return reservas[i];
                }
            }
            throw new ReservaNoExisteException("La reserva con ese ID no existe");
        } catch (ReservaNoExisteException e){
            IO.println(e.getMessage());
            return null;
        }
    }

    //added show teachers for ease of use in main

    public void listarProfesores(){
        for (int i = 0; i < numeroProfesores; i++) {
            if (!isNull(profesores[i])) {
                IO.println(profesores[i].toString());
            }
        }
    }




}
