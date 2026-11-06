package ar.edu.unahur.obj2.catering.Adicionales;

import ar.edu.unahur.obj2.catering.Propuesta.Menu;
import ar.edu.unahur.obj2.catering.Propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdicionalesTest {

    @Test
    void dadoUnServicio_alAgregarMozos_sumaDieciochoMil() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Propuesta propuesta = new MozosAdicionales(servicio);

        assertEquals(78000.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnServicio_alAgregarVajilla_sumaDoceMil() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Propuesta propuesta = new VajillaPremium(servicio);

        assertEquals(72000.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnServicio_alAgregarNocturno_incrementaVeintePorCiento() {
        Servicio servicio = new Servicio("Mesa dulce", 60000.0);

        Propuesta propuesta = new ServicioNocturno(servicio);

        assertEquals(72000.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnMenuConMozos_alAgregarNocturno_incrementaElPrecioAcumulado() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(menu));

        assertEquals(432000.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnServicioNocturnoAplicadoAntesQueMozos_alCalcularPrecio_respetaElOrden() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        Propuesta propuesta = new MozosAdicionales(new ServicioNocturno(menu));

        assertEquals(428400.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnMenuConMozosYNocturno_alDescribir_respetaElOrden() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        String descripcion = new ServicioNocturno(new MozosAdicionales(menu)).descripcion();

        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", descripcion);
    }

    @Test
    void dadoUnMenuConNocturnoYMozos_alDescribir_respetaElOrdenInverso() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));

        String descripcion = new MozosAdicionales(new ServicioNocturno(menu)).descripcion();

        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", descripcion);
    }

    @Test
    void dadoUnBrindis_alRepetirVajilla_sumaDosVecesElCosto() {
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);

        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        assertEquals(74000.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnBrindisConVajillaRepetida_alDescribir_mencionaAmbosAdicionales() {
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        String descripcion = propuesta.descripcion();

        assertEquals("Brindis de bienvenida + Vajilla premium + Vajilla premium", descripcion);
    }

    @Test
    void dadaUnaMesaDulce_alRepetirNocturno_aplicaAmbosIncrementos() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);

        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(dulce));

        assertEquals(86400.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadaUnaMesaDulceConNocturnoRepetido_alDescribir_mencionaAmbasAplicaciones() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(dulce));

        String descripcion = propuesta.descripcion();

        assertEquals("Mesa dulce + Servicio nocturno + Servicio nocturno", descripcion);
    }

    @Test
    void dadoUnEventoCompleto_alAgregarVajilla_sumaSobreElPrecioDelMenu() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Servicio dj = new Servicio("DJ y sonido", 120000.0);
        Servicio brindis = new Servicio("Brindis de bienvenida", 50000.0);
        Menu parrillero = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Menu fiesta = new Menu("Pack Fiesta", 5.0, List.of(barra, dj));
        Menu evento = new Menu("Evento Completo", 20.0, List.of(parrillero, fiesta, brindis));

        Propuesta propuesta = new VajillaPremium(evento);

        assertEquals(530800.0, propuesta.precio(), 0.001);
    }

    @Test
    void dadoUnMenuConVajilla_alDescribir_agregaElNombreDelAdicional() {
        Menu menu = new Menu("Evento Completo", 20.0, List.of(new Servicio("Brindis de bienvenida", 50000.0)));
        Propuesta propuesta = new VajillaPremium(menu);

        String descripcion = propuesta.descripcion();

        assertEquals("Evento Completo + Vajilla premium", descripcion);
    }

    @Test
    void dadoUnMenuConAdicionales_alContarServicios_conservaLaCantidad() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(new VajillaPremium(menu)));

        Integer cantidad = propuesta.cantidadDeServicios();

        assertEquals(Integer.valueOf(3), cantidad);
    }

    @Test
    void dadoUnServicioConAdicionales_alContarServicios_sigueContandoUno() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Propuesta propuesta = new VajillaPremium(new MozosAdicionales(dulce));

        Integer cantidad = propuesta.cantidadDeServicios();

        assertEquals(Integer.valueOf(1), cantidad);
    }

    @Test
    void dadoUnMenuConAdicionales_alConsultarUnServicio_conservaLaInclusion() {
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(parrillada));
        Propuesta propuesta = new ServicioNocturno(new VajillaPremium(new MozosAdicionales(menu)));

        Boolean incluido = propuesta.incluye("Parrillada completa");

        assertTrue(incluido);
    }

    @Test
    void dadoUnAdicional_alConsultarloComoServicio_noEstaIncluido() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Propuesta propuesta = new MozosAdicionales(dulce);

        Boolean incluido = propuesta.incluye("Mozos adicionales");

        assertFalse(incluido);
    }

    @Test
    void dadaUnaPropuestaOriginal_alAgregarAdicionales_conservaSuPrecio() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);

        new ServicioNocturno(new MozosAdicionales(new VajillaPremium(dulce))).precio();

        assertEquals(60000.0, dulce.precio(), 0.001);
    }

    @Test
    void dadaUnaPropuestaOriginal_alAgregarAdicionales_conservaSuDescripcion() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);

        new ServicioNocturno(new MozosAdicionales(new VajillaPremium(dulce))).descripcion();

        assertEquals("Mesa dulce", dulce.descripcion());
    }
}