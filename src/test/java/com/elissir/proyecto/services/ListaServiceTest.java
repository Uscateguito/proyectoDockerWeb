package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Lista;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ListaServiceTest {

    @Autowired
    ListaService listaService;

    @Test
    public void TestcrearLista() {
        Lista listaTest = new Lista("bachatombo");
        Lista listaComparacion = listaService.crearLista(listaTest);
        Assertions.assertEquals(listaTest.getGenero(), listaComparacion.getGenero());
        Assertions.assertEquals(0,listaTest.getNumLikes());

        // Borro el elemnto de la base de datos para no afectar a los otros test

        listaService.eliminarLista(listaComparacion.getId_lista());

    }

    @Test
    public void TestmodificarLista() {
        // La primera parte es crear la lista y la segunda es modificarla
        // 1. Creo la lista

        Lista listaBase = new Lista("bachatombo");
        listaService.crearLista(listaBase);

        // 2. La modifico

        Lista listaModificado = new Lista("tomboKarateca");
        Lista listaComparacion = listaService.actualizarLista(listaBase.getId_lista(), listaModificado);
        Assertions.assertEquals(listaModificado.getGenero(), listaComparacion.getGenero());
//        las constraseñas no son iguales porque se encriptan a pesar de tener los mismos caracteres

//        Assertions.assertEquals(listaModificado.getContrasenia(), listaComparacion.getContrasenia());

        // Borro el elemnto de la base de datos para no afectar a los otros test

        listaService.eliminarLista(listaBase.getId_lista());

    }

    @Test
    public void TestobtenerListaPorId() {
        // La primera parte es crear el lista y la segunda es encontrarla por su ID
        // 1. Creo la lista
        Lista listaBase = new Lista("bachatombo");
        listaService.crearLista(listaBase);

        // 2. La busco
        Lista listaObtenida = listaService.obtenerListaPorId(listaBase.getId_lista());
        Assertions.assertEquals(listaBase.getGenero(), listaObtenida.getGenero());

        // Borro el elemnto de la base de datos para no afectar a los otros test

        listaService.eliminarLista(listaBase.getId_lista());

    }

    // Este test debe realizarse de manera aislada dado que es necesario que no existan otras listas en la base de datos
    // y al ejecutar los otros test, este test se ve afectado
    @Test
    public void TestlistarListas() {
        // Agrego 3 elementos a la base de datos
        Lista listaBase  = new Lista("bachaTombo");
        Lista listaBase2 = new Lista("tomboKarateca");
        Lista listaBase3 = new Lista("tomboVallenato");
        listaService.crearLista(listaBase);
        listaService.crearLista(listaBase2);
        listaService.crearLista(listaBase3);

        // Compruebo que la base de datos efectivamente tenga sólo 3 elementos
        Assertions.assertEquals(3, listaService.listarListas().size());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        listaService.eliminarLista(listaBase.getId_lista());
        listaService.eliminarLista(listaBase2.getId_lista());
        listaService.eliminarLista(listaBase3.getId_lista());

    }

    @Test
    public void TesteliminarLista() {
        // 1. Creo la lista
        Lista listaBase = new Lista("bachatombo");
        listaService.crearLista(listaBase);

        // 2. La elimino
        listaService.eliminarLista(listaBase.getId_lista());
        Assertions.assertNull(listaService.obtenerListaPorId(listaBase.getId_lista()));
    }
}
