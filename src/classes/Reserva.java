package classes;

import exceptions.CapacidadInvalidaException;
import exceptions.ProfesorNoExisteException;
import exceptions.SalonNoExisteException;

public class Reserva {
    private static int counter = 0;
    private int idReserva;
    private String fecha;
    private int horaInicio;
    private int horaFin;
    private int asistentes;
    private Salon salon;
    private Profesor profesor;

    public int getIdReserva() {
        return idReserva;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public int getAsistentes() {
        return asistentes;
    }

    public Salon getSalon() {
        return salon;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public boolean seCruzaCon(Reserva otra) {
        if (this.fecha.equals(otra.getFecha()) && this.salon.equals(otra.getSalon())) {
            return (this.horaInicio < otra.getHoraFin() && this.horaFin > otra.getHoraInicio());
        }
        return false;
    }

    public Reserva(String fecha, int horaInicio, int horaFin, int asistentes, Salon salon, Profesor profesor) throws CapacidadInvalidaException, SalonNoExisteException, ProfesorNoExisteException {

        if (asistentes <= 0 || asistentes > salon.getCapacidad()) {
            throw new CapacidadInvalidaException("El cupo del salón es invalido 🤣boba malparida");
        }

        if ((horaFin - horaInicio > 3) || (horaFin <= horaInicio)) {
            throw new CapacidadInvalidaException("La duración de la reserva no puede ser mayor a 3 horas y debe ser positiva");
        }

        if (salon == null) {
            throw new SalonNoExisteException("El salon no puede ser nulo perrita");
        } else if (profesor == null) {
            throw new ProfesorNoExisteException("El profesor no puede ser nulo perrita");
        }

        idReserva = counter++;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.asistentes = asistentes;
        this.salon = salon;
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "ID de la reserva=" + idReserva +
                ", fecha='" + fecha + '\'' +
                ", Hora Inicio=" + horaInicio +
                ", Hora Fin=" + horaFin +
                ", asistentes=" + asistentes +
                ", salon=" + salon +
                ", profesor=" + profesor +
                '}';
    }
}
