package ar.edu.unahur.obj2.catering.Exceptions;

public class NadaParaRevertirException extends RuntimeException {
    public NadaParaRevertirException() {
        super("No hay modificaciones para revertir");
    }
}
