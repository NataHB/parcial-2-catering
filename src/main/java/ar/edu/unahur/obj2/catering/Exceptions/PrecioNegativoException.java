package ar.edu.unahur.obj2.catering.Exceptions;

public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException() {
        super("El precio no puede ser negativo");
    }

}
