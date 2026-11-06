package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.Exceptions.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

    @Test
    void dadoUnServicio_alConsultarPrecio_devuelveElPrecioCargado() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Double precio = servicio.precio();

        assertEquals(60000.0, precio, 0.001);
    }

    @Test
    void dadoUnServicio_alConsultarDescripcion_devuelveSuNombre() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        String descripcion = servicio.descripcion();

        assertEquals("Mesa dulce", descripcion);
    }

    @Test
    void dadoUnServicio_alContarServicios_devuelveUno() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Integer cantidad = servicio.cantidadDeServicios();

        assertEquals(Integer.valueOf(1), cantidad);
    }

    @Test
    void dadoUnServicio_alConsultarSuNombre_estaIncluido() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Boolean incluido = servicio.incluye("Mesa dulce");

        assertTrue(incluido);
    }

    @Test
    void dadoUnServicio_alConsultarOtroNombre_noEstaIncluido() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Boolean incluido = servicio.incluye("Fotógrafo");

        assertFalse(incluido);
    }

    @Test
    void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        Double precio = -1.0;

        assertThrows(PrecioNegativoException.class, () -> new Servicio("Mesa dulce", precio));
    }

    @Test
    void dadoUnServicioConPrecioCero_alCrearlo_aceptaElPrecio() {
        Double precio = 0.0;

        Servicio servicio = new Servicio("Cortesía", precio);

        assertEquals(0.0, servicio.precio(), 0.001);
    }
}