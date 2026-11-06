package ar.edu.unahur.obj2.catering.Propuesta;
import ar.edu.unahur.obj2.catering.Exceptions.PrecioNegativoException;

public class Servicio implements Propuesta {

    private final String nombre;
    private final Double precio;

    public Servicio(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = validarPrecio(precio);
    }

    public Double validarPrecio(Double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException();
        }
        return precio;
    }

    @Override
    public Double precio() {
        return precio;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public Integer cantidadDeServicios() {
        return 1;
    }

    @Override
    public Boolean incluye(String nombre) {
        return this.nombre.equals(nombre);
    }
}