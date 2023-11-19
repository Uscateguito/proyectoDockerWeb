package com.elissir.proyecto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String apellido;
    private String correo_electronico;
    private String password;
    private int esAdmin;

//     Crea los atributos en formato json con un ejemplo
//     {
//         "nombre": "nombre",
//         "apellido": "apellido",
//         "correo_electronico": "correo_electronico",
//         "password": "password",
//         "esAdmin": true
//     }

}
