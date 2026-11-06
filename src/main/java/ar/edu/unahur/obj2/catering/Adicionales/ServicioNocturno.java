package ar.edu.unahur.obj2.catering.Adicionales;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;

public class ServicioNocturno extends Adicional {
    public ServicioNocturno(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public String descripcion() {
        return propuesta.descripcion() + " + Servicio nocturno";
    }

    @Override
    public Double precio() {
        return propuesta.precio() * 1.20;
    }
}
