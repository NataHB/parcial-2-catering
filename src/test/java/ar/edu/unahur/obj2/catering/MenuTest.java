package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.Exceptions.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    void dadoUnMenuParrillero_alConsultarPrecio_aplicaDiezPorCientoDeDescuento() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Double precio = menu.precio();

        assertEquals(342000.0, precio, 0.001);
    }

    @Test
    void dadoUnPackFiesta_alConsultarPrecio_aplicaCincoPorCientoDeDescuento() {
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Menu menu = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));

        Double precio = menu.precio();

        assertEquals(256500.0, precio, 0.001);
    }

    @Test
    void dadoUnMenuParrillero_alContarServicios_devuelveTres() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Integer cantidad = menu.cantidadDeServicios();

        assertEquals(Integer.valueOf(3), cantidad);
    }

    @Test
    void dadoUnMenuParrillero_alConsultarParrillada_estaIncluida() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Boolean incluido = menu.incluye("Parrillada completa");

        assertTrue(incluido);
    }

    @Test
    void dadoUnMenuParrillero_alConsultarDj_noEstaIncluido() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Boolean incluido = menu.incluye("DJ y sonido");

        assertFalse(incluido);
    }

    @Test
    void dadoUnMenuConDescuentoNegativo_alCrearlo_lanzaDescuentoInvalidoException() {
        Double descuento = -1.0;

        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Inválido", descuento, List.of()));
    }

    @Test
    void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException() {
        Double descuento = 101.0;

        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Inválido", descuento, List.of()));
    }

    @Test
    void dadoUnMenuSinDescuento_alConsultarPrecio_conservaLaSuma() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Sin descuento", 0.0, List.of(servicio));

        Double precio = menu.precio();

        assertEquals(60000.0, precio, 0.001);
    }

    @Test
    void dadoUnMenuConDescuentoTotal_alConsultarPrecio_devuelveCero() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Cortesía", 100.0, List.of(servicio));

        Double precio = menu.precio();

        assertEquals(0.0, precio, 0.001);
    }

    @Test
    void dadoUnEventoCompleto_alConsultarPrecio_descuentaLosPreciosDeLosMenusInternos() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Menu menuParrillero = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Menu packFiesta = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));
        Menu menu = new Menu("Evento Completo", 20.0, List.of(menuParrillero, packFiesta, brindis));

        Double precio = menu.precio();

        assertEquals(518800.0, precio, 0.001);
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaTodosLosNiveles() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Menu menuParrillero = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Menu packFiesta = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));
        Menu menu = new Menu("Evento Completo", 20.0, List.of(menuParrillero, packFiesta, brindis));

        Integer cantidad = menu.cantidadDeServicios();

        assertEquals(Integer.valueOf(6), cantidad);
    }

    @Test
    void dadoUnEventoCompleto_alConsultarDj_loEncuentraEnUnMenuInterno() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Menu menuParrillero = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Menu packFiesta = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));
        Menu menu = new Menu("Evento Completo", 20.0, List.of(menuParrillero, packFiesta, brindis));

        Boolean incluido = menu.incluye("DJ y sonido");

        assertTrue(incluido);
    }

    @Test
    void dadoUnEventoCompleto_alConsultarFotografo_noEstaIncluido() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Menu menuParrillero = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Menu packFiesta = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));
        Menu menu = new Menu("Evento Completo", 20.0, List.of(menuParrillero, packFiesta, brindis));

        Boolean incluido = menu.incluye("Fotógrafo");

        assertFalse(incluido);
    }

    @Test
    void dadoUnMenuVacio_alContarServicios_devuelveCero() {
        Menu menu = new Menu("Vacío", 10.0, List.of());

        Integer cantidad = menu.cantidadDeServicios();

        assertEquals(Integer.valueOf(0), cantidad);
    }
}