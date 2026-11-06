package ar.edu.unahur.obj2.catering.Gestion;
import ar.edu.unahur.obj2.catering.Modificaciones.Modificacion;
import ar.edu.unahur.obj2.catering.Exceptions.NadaParaRevertirException;
import java.util.ArrayList;
import java.util.List;

public class GestorCarta {
private List<Modificacion> historial;

    public GestorCarta() {
        this.historial = new ArrayList<>();
    }

    public void aplicar(Modificacion modificacion) {
        this.aplicarLote(List.of(modificacion));
    }

    public void aplicarLote(List<Modificacion> lote) {
        List<Modificacion> aplicadas = new ArrayList<>();
        try {
            for (Modificacion modificacion : lote) {
                modificacion.aplicar();
                aplicadas.add(modificacion);
            }
        } catch (RuntimeException falla) {
            for (Integer i = aplicadas.size() - 1; i >= 0; i--) {
                aplicadas.get(i).revertir();
            }
            throw falla;
        }
        this.historial.addAll(aplicadas);
    }

    public void revertir() {
        if (this.historial.isEmpty()) {
            throw new NadaParaRevertirException();
        }
        Integer indiceDeLaUltima = this.historial.size() - 1;
        Modificacion ultima = this.historial.get(indiceDeLaUltima);
        ultima.revertir();
        this.historial.remove(ultima);
    }
}
