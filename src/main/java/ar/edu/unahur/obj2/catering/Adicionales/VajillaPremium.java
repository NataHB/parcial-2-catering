package ar.edu.unahur.obj2.catering.Adicionales;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;

public class VajillaPremium extends Adicional {
    public VajillaPremium(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public String descripcion() {
        return propuesta.descripcion() + " + Vajilla premium";
    }

    @Override
    public Double precio() {
        return propuesta.precio() + 12000.0;
    }

}
