package ar.edu.unahur.obj2.catering.Modificaciones;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;

public class QuitarPropuesta implements Modificacion {

    private final Propuesta propuesta;
    private final Menu menu;

    public QuitarPropuesta(Propuesta propuesta, Menu menu) {
        this.propuesta = propuesta;
        this.menu = menu;
    }
    
    @Override
    public void aplicar() {
        menu.quitarPropuesta(propuesta);
    }

    @Override
    public void revertir() {
        menu.agregarPropuesta(propuesta);
    }

}
