package classes;

public class Salon {
    private String codigo;
    private int capacidad;
    private String ubicacion;

    public Salon(String codigo, int capacidad, String ubicacion) {
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }


    @Override
    public String toString() {
        return "Salon{" +
                "codigo='" + codigo + '\'' +
                ", capacidad=" + capacidad +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
