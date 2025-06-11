package edu.richard.sistemaequipos.Servicio;

import edu.richard.sistemaequipos.Modelo.Equipo;
import edu.richard.sistemaequipos.Repositorio.EquipoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoServicio {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepositorio.findAll();
    }

    public Optional<Equipo> obtenerEquipoPorId(Long id) {
        return equipoRepositorio.findById(id);
    }

    public Equipo guardarEquipo(Equipo equipo) {
        validarEquipo(equipo);

        if (equipo.getId() == null && equipo.getFecha() == null) {
            equipo.setFecha(LocalDate.now());
        }

        return equipoRepositorio.save(equipo);
    }

    public Equipo actualizarEquipo(Long id, Equipo equipoActualizado) {
        Optional<Equipo> equipoExistente = equipoRepositorio.findById(id);

        if (equipoExistente.isEmpty()) {
            throw new RuntimeException("Equipo no encontrado");
        }

        validarEquipo(equipoActualizado);

        Equipo equipo = equipoExistente.get();
        equipo.setNombre(equipoActualizado.getNombre());
        equipo.setMarca(equipoActualizado.getMarca());
        equipo.setPrecio(equipoActualizado.getPrecio());
        equipo.setDepartamento(equipoActualizado.getDepartamento());

        if (equipoActualizado.getFecha() != null) {
            equipo.setFecha(equipoActualizado.getFecha());
        }

        return equipoRepositorio.save(equipo);
    }

    public void eliminarEquipo(Long id) {
        if (!equipoRepositorio.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        equipoRepositorio.deleteById(id);
    }

    public List<Equipo> buscarEquiposPorDepartamento(String departamento) {
        return equipoRepositorio.findByDepartamentoIgnoreCase(departamento);
    }

    public boolean existeEquipo(Long id) {
        return equipoRepositorio.existsById(id);
    }

    public long contarEquipos() {
        return equipoRepositorio.count();
    }

    private void validarEquipo(Equipo equipo) {
        if (equipo == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo");
        }

        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo es obligatorio");
        }

        if (equipo.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (equipo.getDepartamento() == null || equipo.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento es obligatorio");
        }
    }
}
