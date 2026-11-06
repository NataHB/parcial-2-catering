package ar.edu.unahur.obj2.catering.Propuesta;

public interface Propuesta {
    public String descripcion();
    public Double precio();
    public Integer cantidadDeServicios();
    public Boolean incluye(String nombre);
}
