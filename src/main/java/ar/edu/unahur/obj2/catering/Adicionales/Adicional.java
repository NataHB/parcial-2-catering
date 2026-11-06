package ar.edu.unahur.obj2.catering.Adicionales;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;

public abstract class Adicional implements Propuesta{
    protected final Propuesta propuesta;

    public Adicional(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    @Override
    public Integer cantidadDeServicios() {
        return propuesta.cantidadDeServicios();
    }

    @Override 
    public Boolean incluye(String nombre) {
        return propuesta.incluye(nombre);
    }
}
