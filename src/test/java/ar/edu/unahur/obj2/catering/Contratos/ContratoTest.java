package ar.edu.unahur.obj2.catering.Contratos;

import ar.edu.unahur.obj2.catering.Adicionales.MozosAdicionales;
import ar.edu.unahur.obj2.catering.Gestion.GestorCarta;
import ar.edu.unahur.obj2.catering.Modificaciones.ActualizarPrecio;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContratoTest {

    @Test
    void dadoUnMenuConMozos_alCrearContrato_fijaElImporteVigente() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Propuesta propuesta = new MozosAdicionales(menu);

        Contrato contrato = new Contrato("Lucía", propuesta);

        assertEquals(360000.0, contrato.getImporte(), 0.001);
    }

    @Test
    void dadoUnMenuConMozos_alCrearContrato_guardaLaDescripcionVigente() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Propuesta propuesta = new MozosAdicionales(menu);

        Contrato contrato = new Contrato("Lucía", propuesta);

        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }

    @Test
    void dadoUnServicioSinAdicionales_alCrearContrato_guardaSuImporte() {
        Propuesta propuesta = new Servicio("Mesa dulce", 60000.0);

        Contrato contrato = new Contrato("Lucía", propuesta);

        assertEquals(60000.0, contrato.getImporte(), 0.001);
    }
    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Contrato contrato = new Contrato("Lucía", menu);
        GestorCarta gestor = new GestorCarta();

        gestor.aplicar(new ActualizarPrecio(parrillada, 260000.0));

        assertEquals(342000.0, contrato.getImporte(), 0.001);
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elMenuReflejaElCambio() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Contrato contrato = new Contrato("Lucía", menu);
        GestorCarta gestor = new GestorCarta();

        gestor.aplicar(new ActualizarPrecio(parrillada, 260000.0));

        assertEquals(360000.0, menu.precio(), 0.001);
    }
}
