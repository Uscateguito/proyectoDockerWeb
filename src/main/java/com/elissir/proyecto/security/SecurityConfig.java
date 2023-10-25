package com.elissir.proyecto.security;

import com.elissir.proyecto.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        Configuración de la cadena de filtros

        return http
//                Autenticación basada en token csrf (Cross-Site Request Forgery) no necesario, porque vamos a utilizar otro tipo de seguridad
                .csrf(csrf -> csrf.disable())
                // Utilizamos un lambda para agregar más de una configuración a las rutas públicas y privadas
                .authorizeHttpRequests(authorize -> authorize
//                       Permitimos que se acceda a la ruta /auth/** sin autenticación (endPoints Públicos)
                                .requestMatchers("/auth/**").permitAll()
//                        Para cualquier otro request, se requiere autenticación (endPoints Privados)
                                .requestMatchers("/admin/**", "/relaciones/genero/*/cancion/*").hasAuthority("ADMIN")
                                .requestMatchers("/persona/**").hasAuthority("PERSONA")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
//                                No se crea una sesión para el usuario
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                Agregamos el filtro de autenticación
                .authenticationProvider(authProvider)
//                Agregamos el filtro de autorización
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


}

