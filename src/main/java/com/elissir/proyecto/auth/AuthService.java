package com.elissir.proyecto.auth;

import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.jwt.JwtService;
import com.elissir.proyecto.repositoryJPA.AdminRepository;
import com.elissir.proyecto.repositoryJPA.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public AuthResponse login(LoginRequest request) {

//        logger.warn("aquí toy");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNombre(), request.getPassword()));

        if (request.getEsAdmin() == 1) {

            Admin admin = adminRepository.findFirstByNombre(request.getNombre());
//            logger.warn("este es el admin que llega: " + admin.toString());
            String token = jwtService.getTokenAdmin(admin);
            return AuthResponse.builder().token(token).build();

        } else {

            Persona persona = personaRepository.findFirstByNombre(request.getNombre());
//            logger.warn("este es el persona que llega: " + persona.toString());
            String token = jwtService.getTokenPersona(persona);
            return AuthResponse.builder().token(token).build();

        }
    }

    public AuthResponse register(RegisterRequest request) {

        if (request.getEsAdmin() == 1) {
            // Crear usuario admin
            Admin admin = Admin.builder()
                    .nombre(request.getNombre())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .build();
            adminRepository.save(admin);
            logger.warn("este es el admin que se registra: " + admin.toString());

            return AuthResponse.builder()
                    .token(jwtService.getTokenAdmin(admin))
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
            logger.warn("este es el persona que se registra: " + persona.toString());

            return AuthResponse.builder().token(jwtService.getTokenPersona(persona)).build();
        }
    }
}
