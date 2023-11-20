package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Cancion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CancionServiceTest {

    @Autowired
    CancionService cancionService;


    @Test
    public void TestcrearCancion() {
        Cancion cancionTest = new Cancion("nombretest", "artistaTest", "albumTest", "duracionTest");
        Cancion cancionComparacion = cancionService.crearCancion(cancionTest);

        Assertions.assertEquals(cancionTest.getNombre(), cancionComparacion.getNombre());
        Assertions.assertEquals(cancionTest.getArtista(), cancionComparacion.getArtista());
        Assertions.assertEquals(cancionTest.getAlbum(), cancionComparacion.getAlbum());
        Assertions.assertEquals(cancionTest.getDuracion(), cancionComparacion.getDuracion());
        Assertions.assertEquals(0,cancionTest.getNumLikes());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        cancionService.eliminarCancion(cancionComparacion.getId_cancion());
    }

    @Test
    public void TestmodificarCancion() {
        // La primera parte es crear el cancion y la segunda es modificarlo
        // 1. Creo la cancion

        Cancion cancionBase = new Cancion("nombretest", "artistaTest", "albumTest", "duracionTest");
        cancionService.crearCancion(cancionBase);

        // 2. La modifico

        Cancion cancionModificado = new Cancion("nombretest2", "artistaTest2", "albumTest2", "duracionTest2");
        Cancion cancionComparacion = cancionService.actualizarCancion(cancionBase.getId_cancion(), cancionModificado);
        Assertions.assertEquals(cancionModificado.getNombre(), cancionComparacion.getNombre());
//        las constraseñas no son iguales porque se encriptan a pesar de tener los mismos caracteres

//        Assertions.assertEquals(cancionModificado.getContrasenia(), cancionComparacion.getContrasenia());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        cancionService.eliminarCancion(cancionComparacion.getId_cancion());
    }

    @Test
    public void TestobtenerCancionPorId() {
        // La primera parte es crear la cancion y la segunda es encontrarla por su ID
        // 1. Creo la cancion
        Cancion cancionBase = new Cancion("nombretest", "artistaTest", "albumTest", "duracionTest");
        cancionService.crearCancion(cancionBase);

        // 2. La busco
        Cancion cancionObtenida = cancionService.obtenerCancionPorId(cancionBase.getId_cancion());
        Assertions.assertEquals(cancionBase.getNombre(), cancionObtenida.getNombre());
        Assertions.assertEquals(cancionBase.getArtista(), cancionObtenida.getArtista());
        Assertions.assertEquals(cancionBase.getAlbum(), cancionObtenida.getAlbum());
        Assertions.assertEquals(cancionBase.getDuracion(), cancionObtenida.getDuracion());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        cancionService.eliminarCancion(cancionObtenida.getId_cancion());
    }

    @Test
    public void TestlistarCancions() {
        Cancion cancionBase  = new Cancion("nombretest1", "artistaTest1", "albumTest1", "duracionTest1");
        Cancion cancionBase2 = new Cancion("nombretest2", "artistaTest2", "albumTest2", "duracionTest2");
        Cancion cancionBase3 = new Cancion("nombretest3", "artistaTest3", "albumTest3", "duracionTest3");
        cancionService.crearCancion(cancionBase);
        cancionService.crearCancion(cancionBase2);
        cancionService.crearCancion(cancionBase3);
        Assertions.assertEquals(3, cancionService.listarCanciones().size());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        cancionService.eliminarCancion(cancionBase.getId_cancion());
        cancionService.eliminarCancion(cancionBase2.getId_cancion());
        cancionService.eliminarCancion(cancionBase3.getId_cancion());
    }

    @Test
    public void TesteliminarCancion() {
        // 1. Creo la cancion
        Cancion cancionBase = new Cancion("nombretest1", "artistaTest1", "albumTest1", "duracionTest1");
        cancionService.crearCancion(cancionBase);

        // 2. La elimino
        cancionService.eliminarCancion(cancionBase.getId_cancion());
        Assertions.assertNull(cancionService.obtenerCancionPorId(cancionBase.getId_cancion()));
    }
}
