package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.entidades.Persona_Cancion;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class Persona_CancionServiceTest {

    @Autowired
    Persona_CancionService persona_cancionService;

    @Test
    public void Testcrearpersona_cancion() {

        // Objetos para testing

        Persona_Cancion persona_cancionTest = new Persona_Cancion();
        Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
        Cancion cancion = new Cancion("nombreTest", "artistaTest", "albumTest", "duracionTest");
        persona_cancionTest.setPersona(persona);
        persona_cancionTest.setCancion(cancion);
        Persona_Cancion persona_cancionComparacion = persona_cancionService.crearpersona_cancion(persona_cancionTest);

        // Testing
        Assertions.assertEquals(persona_cancionTest.getLike(), persona_cancionComparacion.getLike());
        Assertions.assertEquals(persona_cancionTest.getPersona(), persona_cancionComparacion.getPersona());
        Assertions.assertEquals(persona_cancionTest.getCancion(), persona_cancionComparacion.getCancion());

        // Borrar de la db
        persona_cancionService.eliminarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona());
    }

    @Test
    public void TestObtenerPersona_Cancion() {

        // 1. Creo el objeto para el testing

        Persona_Cancion persona_cancionTest = new Persona_Cancion();
        Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
        Cancion cancion = new Cancion("nombreTest", "artistaTest", "albumTest", "duracionTest");
        persona_cancionTest.setPersona(persona);
        persona_cancionTest.setCancion(cancion);
        Persona_Cancion persona_cancionComparacion = persona_cancionService.crearpersona_cancion(persona_cancionTest);

        // 2. Se hace la busqueda

        Persona_Cancion persona_cancionBuscada = persona_cancionService.obtenerpersona_cancionPorObjetos(cancion, persona);

        // 3. Se hace el testing

        Assertions.assertEquals(persona_cancionComparacion.getLike(), persona_cancionBuscada.getLike());
        Assertions.assertEquals(persona_cancionComparacion.getPersona().getNombre(), persona_cancionBuscada.getPersona().getNombre());
        Assertions.assertEquals(persona_cancionComparacion.getCancion().getNombre(), persona_cancionBuscada.getCancion().getNombre());

        // 4. Se borra de la db
        persona_cancionService.eliminarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona());

    }

    @Test
    public void TestActualizarPersona_Cancion(){

        // 1. Creo el objeto para el testing

        Persona_Cancion persona_cancionTest = new Persona_Cancion();
        Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
        Cancion cancion = new Cancion("nombreTest", "artistaTest", "albumTest", "duracionTest");
        persona_cancionTest.setPersona(persona);
        persona_cancionTest.setCancion(cancion);
        Persona_Cancion persona_cancionComparacion = persona_cancionService.crearpersona_cancion(persona_cancionTest);

        // 2. Se hace la modificación

        Persona_Cancion persona_cancionBuscada = persona_cancionService.actualizarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona(), persona_cancionComparacion);

        // 3. Se hace el testing

        Assertions.assertEquals(persona_cancionComparacion.getLike(), 0);

        persona_cancionBuscada = persona_cancionService.actualizarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona(), persona_cancionComparacion);

        Assertions.assertEquals(persona_cancionComparacion.getLike(), 1);

        // 4. Se borra de la db

        persona_cancionService.eliminarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona());

    }

    @Test
    public void TestEliminarPersona_Cancion(){

        // 1. Creo el objeto para el testing

        Persona_Cancion persona_cancionTest = new Persona_Cancion();
        Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
        Cancion cancion = new Cancion("nombreTest", "artistaTest", "albumTest", "duracionTest");
        persona_cancionTest.setPersona(persona);
        persona_cancionTest.setCancion(cancion);
        Persona_Cancion persona_cancionComparacion = persona_cancionService.crearpersona_cancion(persona_cancionTest);

        // 2. Se hace la eliminación

        persona_cancionService.eliminarpersona_cancion(persona_cancionComparacion.getCancion(), persona_cancionComparacion.getPersona());

        // 3. Se busca el objeto eliminado

        Persona_Cancion persona_cancionBuscada = persona_cancionService.obtenerpersona_cancionPorObjetos(cancion, persona);

        // 4. Se hace el testing

        Assertions.assertNull(persona_cancionBuscada);

    }


}
