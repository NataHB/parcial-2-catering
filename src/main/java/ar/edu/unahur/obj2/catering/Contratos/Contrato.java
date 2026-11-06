package ar.edu.unahur.obj2.catering.Contratos;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;

public class Contrato {
    private final String cliente;
    private final Double importe;
    private final String descripcion;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.importe = propuesta.precio();
        this.descripcion = propuesta.descripcion();
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getImporte() {
        return importe;
    }

    
}
