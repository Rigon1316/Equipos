package edu.richard.sistemaequipos.Repositorio;

import edu.richard.sistemaequipos.Modelo.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {
    
}