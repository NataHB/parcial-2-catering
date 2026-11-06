package ar.edu.unahur.obj2.catering.Modificaciones;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;

public class ActualizarPrecio implements Modificacion {

    private final Servicio servicio;
    private final Double nuevoPrecio;
    private Double precioAnterior;

    public ActualizarPrecio(Servicio servicio, Double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public void aplicar() {
        precioAnterior = servicio.precio();
        servicio.actualizarPrecio(nuevoPrecio);
    }

    @Override
    public void revertir() {
        servicio.actualizarPrecio(precioAnterior);
    }

}
