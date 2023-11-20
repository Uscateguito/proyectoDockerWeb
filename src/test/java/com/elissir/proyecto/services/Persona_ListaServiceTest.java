package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Lista;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.entidades.Persona_Lista;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class Persona_ListaServiceTest {

    @Autowired
    Persona_ListaService persona_listaService;

    @Test
    public void Testcrearpersona_lista(){

            // Objetos para testing

            Persona_Lista persona_listaTest = new Persona_Lista();
            Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
            Lista lista = new Lista("nombreTest");
            persona_listaTest.setPersona(persona);
            persona_listaTest.setLista(lista);
            Persona_Lista persona_listaComparacion = persona_listaService.crearpersona_lista(persona_listaTest);

            // Testing
            Assertions.assertEquals(persona_listaTest.getLike(), persona_listaComparacion.getLike());
            Assertions.assertEquals(persona_listaTest.getPersona(), persona_listaComparacion.getPersona());
            Assertions.assertEquals(persona_listaTest.getLista(), persona_listaComparacion.getLista());

            // Borrar de la db
            persona_listaService.eliminarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona());
    }

    @Test
    public void TestObtenerPersona_lista() {

            // 1. Creo el objeto para el testing

            Persona_Lista persona_listaTest = new Persona_Lista();
            Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
            Lista lista = new Lista("nombreTest");
            persona_listaTest.setPersona(persona);
            persona_listaTest.setLista(lista);
            Persona_Lista persona_listaComparacion = persona_listaService.crearpersona_lista(persona_listaTest);

            // 2. Se hace la busqueda

            Persona_Lista persona_listaBuscada = persona_listaService.obtenerpersona_listaPorObjetos(lista, persona);

            // 3. Se hace el testing

            Assertions.assertEquals(persona_listaComparacion.getLike(), persona_listaBuscada.getLike());
            Assertions.assertEquals(persona_listaComparacion.getPersona().getNombre(), persona_listaBuscada.getPersona().getNombre());
            Assertions.assertEquals(persona_listaComparacion.getLista().getGenero(), persona_listaBuscada.getLista().getGenero());

            // 4. Se borra de la db
            persona_listaService.eliminarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona());
    }

    @Test
    public void TestActualizarPersona_Lista() {

                // 1. Creo el objeto para el testing

                Persona_Lista persona_listaTest = new Persona_Lista();
                Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
                Lista lista = new Lista("nombreTest");
                persona_listaTest.setPersona(persona);
                persona_listaTest.setLista(lista);
                Persona_Lista persona_listaComparacion = persona_listaService.crearpersona_lista(persona_listaTest);

                // 2. Se hace la modificación

                Persona_Lista persona_listaBuscada = persona_listaService.actualizarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona(), persona_listaComparacion);

                // 3. Se hace el testing

                Assertions.assertEquals(persona_listaComparacion.getLike(), 0);

                persona_listaBuscada = persona_listaService.actualizarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona(), persona_listaComparacion);

                Assertions.assertEquals(persona_listaComparacion.getLike(), 1);

                // 4. Se borra de la db
                persona_listaService.eliminarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona());
    }

    @Test
    public void TestEliminarPersona_lista(){

                    // 1. Creo el objeto para el testing

                    Persona_Lista persona_listaTest = new Persona_Lista();
                    Persona persona = new Persona("nombreTest", "apellidoTest", "emailTest", "contraseniaTest");
                    Lista lista = new Lista("nombreTest");
                    persona_listaTest.setPersona(persona);
                    persona_listaTest.setLista(lista);
                    Persona_Lista persona_listaComparacion = persona_listaService.crearpersona_lista(persona_listaTest);

                    // 2. Se hace la eliminación

                    persona_listaService.eliminarpersona_lista(persona_listaComparacion.getLista(), persona_listaComparacion.getPersona());

                    // 3. Se busca el objeto eliminado

                    Persona_Lista persona_listaBuscada = persona_listaService.obtenerpersona_listaPorObjetos(lista, persona);

                    // 4. Se hace el testing

                    Assertions.assertNull(persona_listaBuscada);
    }

}
