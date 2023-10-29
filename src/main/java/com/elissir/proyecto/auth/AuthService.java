package com.elissir.proyecto.auth;

import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.jwt.JwtService;
import com.elissir.proyecto.repository.AdminRepository;
import com.elissir.proyecto.repository.PersonaRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
// RequiredArgsConstructor crea un constructor con los atributos marcados como final
// así no es necesario el autowired de cada uno de ellos
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PersonaRepository personaRepository;

    private final PasswordEncoder passwordEncoder;
    //    Objeto relacionado al token
    private final JwtService jwtService;
    //    El autentication manager sirve para validar el usuario y contraseña
    private final AuthenticationManager authenticationManager;
    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthService.class);

//    Se solicita el token por HTTPS al controlador del otro proyecto
    public AuthResponse login(LoginRequest request) throws URISyntaxException {

//        logger.warn("aquí toy");
        // Creo un usuario en un objeto tipo Json, para enviarlo por la solicitud POST

        return AuthResponse.builder()
                .token(getTokenFromLoginRequest(request))
                .build();

    }


    public AuthResponse register(RegisterRequest request) {

        if (request.getEsAdmin()) {
            // Crear usuario admin
            Admin admin = Admin.builder()
                    .nombre(request.getNombre())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .build();
            adminRepository.save(admin);

            return AuthResponse.builder()
                    .token(getTokenFromRegisterRequest(request))
                    .build();

        } else {
            // Crear usuario normal
            Persona persona = Persona.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .correoElectronico(request.getCorreo_electronico())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .build();
            personaRepository.save(persona);

            return AuthResponse.builder()
                    .token(getTokenFromRegisterRequest(request))
                    .build();
        }
    }

    public String getTokenFromLoginRequest(LoginRequest request) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("nombre", request.getNombre());
        jsonObject.addProperty("password", request.getPassword());
        jsonObject.addProperty("esAdmin", request.getEsAdmin());

        // Creo un objeto httpClient

        HttpClient httpClient = HttpClient.newBuilder().build();

        // URL a la que se enviará la solicitud POST
        String url = System.getenv("PROYECTO_AUTH_LOGIN"); // Reemplaza con la URL de destino

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json") // Establecer el tipo de contenido del cuerpo de la solicitud
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())) // Utilizar el JSON generado como String
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int lenghtOfResponse = response.body().length() - 1;
            String responseBody = response.body().substring(9, lenghtOfResponse).replace("\"", "");

            return responseBody;

        } catch (Exception e) {
            System.out.println("Error al enviar la solicitud POST");
            e.printStackTrace();
        }

        throw new RuntimeException("Error al enviar la solicitud POST");

    }

    public String getTokenFromRegisterRequest(RegisterRequest request) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("nombre", request.getNombre());
        jsonObject.addProperty("apellido", request.getApellido());
        jsonObject.addProperty("correo_electronico", request.getCorreo_electronico());
        jsonObject.addProperty("password", request.getPassword());
        jsonObject.addProperty("esAdmin", request.getEsAdmin());

        // Creo un objeto httpClient

        HttpClient httpClient = HttpClient.newBuilder().build();

        // URL a la que se enviará la solicitud POST
        String url = System.getenv("PROYECTO_AUTH_REGISTER"); // Reemplaza con la URL de destino

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json") // Establecer el tipo de contenido del cuerpo de la solicitud
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())) // Utilizar el JSON generado como String
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int lenghtOfResponse = response.body().length() - 1;
            String responseBody = response.body().substring(9, lenghtOfResponse).replace("\"", "");

            return responseBody;

        } catch (Exception e) {
            System.out.println("Error al enviar la solicitud POST");
            e.printStackTrace();
        }

        throw new RuntimeException("Error al enviar la solicitud POST");
    }
}
