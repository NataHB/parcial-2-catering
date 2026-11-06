package ar.edu.unahur.obj2.catering.Gestion;

import ar.edu.unahur.obj2.catering.Exceptions.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.Exceptions.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.Exceptions.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.Modificaciones.ActualizarPrecio;
import ar.edu.unahur.obj2.catering.Modificaciones.AgregarPropuesta;
import ar.edu.unahur.obj2.catering.Modificaciones.Modificacion;
import ar.edu.unahur.obj2.catering.Modificaciones.QuitarPropuesta;
import ar.edu.unahur.obj2.catering.Propuesta.Menu;
import ar.edu.unahur.obj2.catering.Propuesta.Servicio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorCartaTest {

    @Test
    void dadaUnaSecuenciaInmediata_alAplicarYRevertir_respetaLosPreciosEsperados() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        GestorCarta gestor = new GestorCarta();
        List<Double> precios = new ArrayList<>();

        precios.add(menu.precio());
        gestor.aplicar(new AgregarPropuesta(fotografo, menu));
        precios.add(menu.precio());
        gestor.aplicar(new ActualizarPrecio(parrillada, 260000.0));
        precios.add(menu.precio());
        gestor.aplicar(new QuitarPropuesta(dulce, menu));
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());

        assertEquals(List.of(342000.0, 432000.0, 450000.0, 396000.0, 450000.0, 432000.0, 342000.0), precios);
    }

    @Test
    void dadoUnHistorialVacio_alRevertir_lanzaNadaParaRevertirException() {
        GestorCarta gestor = new GestorCarta();

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadaUnaSecuenciaCompletamenteRevertida_alRevertirOtraVez_lanzaExcepcion() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú simple", 10.0, List.of(dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        GestorCarta gestor = new GestorCarta();
        gestor.aplicar(new AgregarPropuesta(fotografo, menu));
        gestor.aplicar(new ActualizarPrecio(dulce, 70000.0));
        gestor.aplicar(new QuitarPropuesta(dulce, menu));
        gestor.revertir();
        gestor.revertir();
        gestor.revertir();

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnLoteValido_alAplicarYRevertir_registraCadaModificacionPorSeparado() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        GestorCarta gestor = new GestorCarta();
        List<Modificacion> lote = List.of(
                new AgregarPropuesta(fotografo, menu),
                new ActualizarPrecio(parrillada, 260000.0),
                new QuitarPropuesta(dulce, menu)
        );
        List<Double> precios = new ArrayList<>();

        gestor.aplicarLote(lote);
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());
        gestor.revertir();
        precios.add(menu.precio());

        assertEquals(List.of(396000.0, 450000.0, 432000.0, 342000.0), precios);
    }

    @Test
    void dadoUnLoteCompletamenteRevertido_alRevertirOtraVez_elHistorialEstaVacio() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú simple", 10.0, List.of(dulce));
        GestorCarta gestor = new GestorCarta();
        gestor.aplicarLote(List.of(
                new ActualizarPrecio(dulce, 70000.0),
                new QuitarPropuesta(dulce, menu)
        ));
        gestor.revertir();
        gestor.revertir();

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElCatalogoComoEstaba() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        GestorCarta gestor = new GestorCarta();
        gestor.aplicar(new AgregarPropuesta(fotografo, menu));
        List<Modificacion> lote = List.of(
                new ActualizarPrecio(parrillada, 260000.0),
                new QuitarPropuesta(barra, menu)
        );

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(lote));
        assertEquals(List.of(432000.0, 240000.0), List.of(menu.precio(), parrillada.precio()));
    }

    @Test
    void dadoUnLoteFallido_alRevertir_deshaceSoloLaModificacionAnteriorAlLote() {
        Servicio entradas = new Servicio("Mesa de entradas", 80000.0);
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(entradas, parrillada, dulce));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        GestorCarta gestor = new GestorCarta();
        gestor.aplicar(new AgregarPropuesta(fotografo, menu));
        List<Modificacion> lote = List.of(
                new ActualizarPrecio(parrillada, 260000.0),
                new QuitarPropuesta(barra, menu)
        );

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(lote));
        gestor.revertir();
        assertEquals(342000.0, menu.precio(), 0.001);
    }

    @Test
    void dadoUnLoteFallidoYLaOperacionPreviaRevertida_alRevertir_noQuedaHistorial() {
        Servicio parrillada = new Servicio("Parrillada completa", 240000.0);
        Menu menu = new Menu("Menú Parrillero", 10.0, List.of(parrillada));
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        GestorCarta gestor = new GestorCarta();
        gestor.aplicar(new AgregarPropuesta(fotografo, menu));
        List<Modificacion> lote = List.of(
                new ActualizarPrecio(parrillada, 260000.0),
                new QuitarPropuesta(barra, menu)
        );

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(lote));
        gestor.revertir();
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadaUnaActualizacionInvalida_alAplicarla_noSeRegistraEnElHistorial() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        GestorCarta gestor = new GestorCarta();
        Modificacion modificacion = new ActualizarPrecio(dulce, -1.0);

        assertThrows(PrecioNegativoException.class, () -> gestor.aplicar(modificacion));
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnQuitadoInvalido_alAplicarlo_noSeRegistraEnElHistorial() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio barra = new Servicio("Barra de tragos", 150000.0);
        Menu menu = new Menu("Menú simple", 0.0, List.of(dulce));
        GestorCarta gestor = new GestorCarta();

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicar(new QuitarPropuesta(barra, menu)));
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertir());
    }

    @Test
    void dadoUnLoteQueAgregaYQuitaLaMismaPropuesta_alFallar_revierteEnOrdenInverso() {
        Servicio dulce = new Servicio("Mesa dulce", 60000.0);
        Servicio fotografo = new Servicio("Fotógrafo", 100000.0);
        Menu menu = new Menu("Menú simple", 10.0, List.of(dulce));
        GestorCarta gestor = new GestorCarta();
        List<Modificacion> lote = List.of(
                new AgregarPropuesta(fotografo, menu),
                new QuitarPropuesta(fotografo, menu),
                new ActualizarPrecio(dulce, 70000.0),
                new ActualizarPrecio(dulce, -1.0)
        );

        assertThrows(PrecioNegativoException.class, () -> gestor.aplicarLote(lote));
        assertEquals(List.of(54000.0, 60000.0), List.of(menu.precio(), dulce.precio()));
    }
}
