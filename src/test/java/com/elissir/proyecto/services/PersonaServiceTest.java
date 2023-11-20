package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PersonaServiceTest {

	@Autowired
	PersonaService personaService;

	@Test
	public void TestCambioPassword() {
		Persona persona = new Persona("Elissir", "Gonzalez", "1234", "a@b.com");
		Assertions.assertNotEquals("1234", persona.getContrasenia());
	}

	@Test
	public void TestcrearPersona() {
		Persona personatest = new Persona("test1_nombre", "test1_apellido", "test_correo", "test_contrasenia");
		Persona personaComparacion = personaService.crearPersona(personatest);
		Assertions.assertEquals(personatest.getNombre(), personaComparacion.getNombre());
		Assertions.assertEquals(personatest.getApellido(), personaComparacion.getApellido());
		Assertions.assertEquals(personatest.getCorreo_electronico(), personaComparacion.getCorreo_electronico());
		Assertions.assertEquals(personatest.getContrasenia(), personaComparacion.getContrasenia());

		// Borro el elemnto de la base de datos para no afectar a los otros test
		personaService.eliminarPersona(personaComparacion.getId_persona());
	}

	@Test
	public void TestmodificarPersona() {
		// 1. Creo la persona
		Persona personaBase = new Persona("test1_nombre", "test1_apellido", "test_correo", "test_contrasenia");
		personaService.crearPersona(personaBase);

		// 2. La modifico
		Persona personaModificada = new Persona("test2_nombre", "test2_apellido", "test_correo", "test_contrasenia");
		Persona personaComparacion = personaService.actualizarPersona(personaBase.getId_persona(), personaModificada);

		Assertions.assertEquals(personaModificada.getNombre(), personaComparacion.getNombre());
		Assertions.assertEquals(personaModificada.getApellido(), personaComparacion.getApellido());
		Assertions.assertEquals(personaModificada.getCorreo_electronico(), personaComparacion.getCorreo_electronico());

		// Borro el elemnto de la base de datos para no afectar a los otros test
		personaService.eliminarPersona(personaComparacion.getId_persona());
	}

	@Test
	public void TestlistarPersonas() {
		Persona personaBase = new Persona("test1_nombre", "test1_apellido", "test_correo", "test_contrasenia");
		Persona personaBase2 = new Persona("test2_nombre", "test2_apellido", "test_correo", "test_contrasenia");
		Persona personaBase3 = new Persona("test3_nombre", "test3_apellido", "test_correo", "test_contrasenia");
		personaService.crearPersona(personaBase);
		personaService.crearPersona(personaBase2);
		personaService.crearPersona(personaBase3);
		Assertions.assertEquals(3, personaService.listarPersonas().size());

		// Borro el elemnto de la base de datos para no afectar a los otros test
		personaService.eliminarPersona(personaBase.getId_persona());
		personaService.eliminarPersona(personaBase2.getId_persona());
		personaService.eliminarPersona(personaBase3.getId_persona());

	}

	@Test
	public void TesteliminarPersona() {
		// 1. Creo la persona
		Persona personaBase = new Persona("test1_nombre", "test1_apellido", "test_correo", "test_contrasenia");
		personaService.crearPersona(personaBase);

		// 2. La elimino
		personaService.eliminarPersona(personaBase.getId_persona());
		Assertions.assertNull(personaService.obtenerPersonaPorId(personaBase.getId_persona()));
	}


}


