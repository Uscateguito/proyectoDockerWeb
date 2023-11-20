package com.elissir.proyecto.repositoryJPA;

import com.elissir.proyecto.entidades.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
}
