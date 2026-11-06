package ar.edu.unahur.obj2.catering.Modificaciones;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;

public class AgregarPropuesta implements Modificacion {
    private final Propuesta propuesta;
    private final Menu menu;

    public AgregarPropuesta(Propuesta propuesta, Menu menu) {
        this.propuesta = propuesta;
        this.menu = menu;
    }
    
    @Override
    public void aplicar() {
        menu.agregarPropuesta(propuesta);
    }

    @Override
    public void revertir() {
        menu.quitarPropuesta(propuesta);
    }

}
