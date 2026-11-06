package ar.edu.unahur.obj2.catering.Propuesta;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.catering.Exceptions.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.Exceptions.PropuestaNoIncluidaException;

public class Menu implements Propuesta {

    private final String nombre;
    private final Double descuento;
    private final List<Propuesta> propuestas;

    public Menu(String nombre, Double descuento, List<Propuesta> propuestas) {
        this.nombre = nombre;
        this.descuento = validarDescuento(descuento);
        this.propuestas = new ArrayList<>(propuestas);
    }

    public Double validarDescuento(Double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new DescuentoInvalidoException();
        }
        return descuento;
    }

    @Override
    public Double precio() {
        return propuestas.stream().mapToDouble(propuesta -> propuesta.precio()).sum() * (100 - descuento) / 100;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public Integer cantidadDeServicios() {
        return propuestas.stream().mapToInt(propuesta -> propuesta.cantidadDeServicios()).sum();
    }

    @Override
    public Boolean incluye(String nombre) {
        return propuestas.stream().anyMatch(propuesta -> propuesta.incluye(nombre));
    }

    public void agregarPropuesta(Propuesta propuesta) {
        propuestas.add(propuesta);
    }

    public void quitarPropuesta(Propuesta propuesta) {
        if (!propuestas.contains(propuesta)) {
            throw new PropuestaNoIncluidaException();
        }
        propuestas.remove(propuesta);
    }
}