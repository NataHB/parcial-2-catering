package ar.edu.unahur.obj2.catering.Adicionales;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;

public class MozosAdicionales extends Adicional {
    public MozosAdicionales(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public String descripcion() {
        return propuesta.descripcion() + " + Mozos adicionales";
    }

    @Override
    public Double precio() {
        return propuesta.precio() + 18000.0;
    }
}
