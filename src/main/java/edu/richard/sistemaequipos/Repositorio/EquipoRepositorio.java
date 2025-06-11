package edu.richard.sistemaequipos.Repositorio;

import edu.richard.sistemaequipos.Modelo.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {

    // Métodos existentes
    long countByDepartamentoIgnoreCase(String departamento);
    List<Equipo> findByUltimaFechaModificacionAfter(LocalDateTime ultimaFechaModificacionAfter);

    // Métodos que faltan para el servicio
    List<Equipo> findByDepartamentoIgnoreCase(String departamento);

    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
}