package ar.edu.unahur.obj2.catering.Modificaciones;

import ar.edu.unahur.obj2.catering.Exceptions.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.Exceptions.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.Gestion.GestorCarta;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModificacionesTest {

    @Test
    void dadoUnFotografo_alAgregarlo_alteraElPrecioDelMenu() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Modificacion modificacion = new AgregarPropuesta(fotografo, menu);

        modificacion.aplicar();

        assertEquals(432000.0, menu.precio(), 0.001);
    }

    @Test
    void dadoUnAgregadoAplicado_alRevertirlo_recuperaElPrecioOriginal() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Modificacion modificacion = new AgregarPropuesta(fotografo, menu);
        modificacion.aplicar();

        modificacion.revertir();

        assertEquals(342000.0, menu.precio(), 0.001);
    }

    @Test
    void dadaUnaMesaDulceIncluida_alQuitarla_recalculaElPrecioDelMenu() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Modificacion modificacion = new QuitarPropuesta(dulce, menu);

        modificacion.aplicar();

        assertEquals(288000.0, menu.precio(), 0.001);
    }

    @Test
    void dadoUnQuitadoAplicado_alRevertirlo_recuperaElPrecioOriginal() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Modificacion modificacion = new QuitarPropuesta(dulce, menu);
        modificacion.aplicar();

        modificacion.revertir();

        assertEquals(342000.0, menu.precio(), 0.001);
    }

    @Test
    void dadaUnaActualizacionDePrecio_alAplicarla_actualizaElServicio() {
        Servicio servicio = new Servicio("Parrillada completa", 240000.0);
        Modificacion modificacion = new ActualizarPrecio(servicio, 260000.0);

        modificacion.aplicar();

        assertEquals(260000.0, servicio.precio(), 0.001);
    }

    @Test
    void dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior() {
        Servicio servicio = new Servicio("Parrillada completa", 240000.0);
        Modificacion modificacion = new ActualizarPrecio(servicio, 260000.0);
        modificacion.aplicar();

        modificacion.revertir();

        assertEquals(240000.0, servicio.precio(), 0.001);
    }

    @Test
    void dadasDosActualizacionesCreadasAntesDeAplicar_alRevertir_restauranCadaPrecio() {
        Servicio servicio = new Servicio("Parrillada completa", 240000.0);
        Modificacion primera = new ActualizarPrecio(servicio, 260000.0);
        Modificacion segunda = new ActualizarPrecio(servicio, 300000.0);
        GestorCarta gestor = new GestorCarta();
        List<Double> precios = new ArrayList<>();

        gestor.aplicar(primera);
        gestor.aplicar(segunda);
        precios.add(servicio.precio());
        gestor.revertir();
        precios.add(servicio.precio());
        gestor.revertir();
        precios.add(servicio.precio());

        assertEquals(List.of(300000.0, 260000.0, 240000.0), precios);
    }

    @Test
    void dadoUnPrecioNegativo_alActualizar_lanzaExcepcionYConservaElPrecio() {
        Servicio servicio = new Servicio("Parrillada completa", 240000.0);
        Modificacion modificacion = new ActualizarPrecio(servicio, -1.0);

        assertThrows(PrecioNegativoException.class, () -> modificacion.aplicar());
        assertEquals(240000.0, servicio.precio(), 0.001);
    }

    @Test
    void dadaUnaPropuestaNoIncluida_alQuitarla_lanzaExcepcionSinAlterarElMenu() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú simple", 10.0, List.of(dulce));
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Modificacion modificacion = new QuitarPropuesta(barra, menu);

        assertThrows(PropuestaNoIncluidaException.class, () -> modificacion.aplicar());
        assertEquals(54000.0, menu.precio(), 0.001);
    }

    @Test
    void dadoUnServicioIncluidoIndirectamente_alQuitarloDelExterior_lanzaExcepcion() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu interior = new Menu("Menú interior", 10.0, List.of(dulce));
        Menu exterior = new Menu("Menú exterior", 20.0, List.of(interior));
        Modificacion modificacion = new QuitarPropuesta(dulce, exterior);

        assertThrows(PropuestaNoIncluidaException.class, () -> modificacion.aplicar());
    }
}
