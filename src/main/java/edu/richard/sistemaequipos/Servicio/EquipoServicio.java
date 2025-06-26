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
    @Autowired
    private ResponsableServicio responsableServicio;
    @Autowired
    private OficinaServicio oficinaServicio;

    /** Lista todos los equipos. */
    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepositorio.findAll();
    }

    /** Busca un equipo por id. */
    public Optional<Equipo> obtenerEquipoPorId(Long id) {
        return equipoRepositorio.findById(id);
    }

    /** Guarda un equipo. */
    public Equipo guardarEquipo(Equipo equipo) {
        // Asignar responsable completo si solo viene el id
        if (equipo.getResponsable() != null && equipo.getResponsable().getId() != null) {
            responsableServicio.buscarPorId(equipo.getResponsable().getId())
                .ifPresent(equipo::setResponsable);
        }
        // Asignar oficina completa si solo viene el id
        if (equipo.getOficina() != null && equipo.getOficina().getId() != null) {
            oficinaServicio.buscarPorId(equipo.getOficina().getId())
                .ifPresent(equipo::setOficina);
        }
        if (equipo.getFechaAdquisicion() == null) {
            equipo.setFechaAdquisicion(LocalDate.now());
        }
        equipo.setUltimaFechaModificacion(LocalDate.now());
        return equipoRepositorio.save(equipo);
    }

    /** Actualiza un equipo. */
    public Equipo actualizarEquipo(Long id, Equipo equipoActualizado) {
        Optional<Equipo> optionalEquipo = equipoRepositorio.findById(id);
        if (optionalEquipo.isPresent()) {
            Equipo equipo = optionalEquipo.get();
            equipo.setNombre(equipoActualizado.getNombre());
            equipo.setMarca(equipoActualizado.getMarca());
            equipo.setModelo(equipoActualizado.getModelo());
            equipo.setPrecio(equipoActualizado.getPrecio());
            equipo.setDepartamento(equipoActualizado.getDepartamento());
            equipo.setEstado(equipoActualizado.getEstado());
            equipo.setUltimaFechaModificacion(LocalDate.now());
            // Actualizar responsable y oficina si vienen en la petición
            if (equipoActualizado.getResponsable() != null && equipoActualizado.getResponsable().getId() != null) {
                responsableServicio.buscarPorId(equipoActualizado.getResponsable().getId())
                    .ifPresent(equipo::setResponsable);
            }
            if (equipoActualizado.getOficina() != null && equipoActualizado.getOficina().getId() != null) {
                oficinaServicio.buscarPorId(equipoActualizado.getOficina().getId())
                    .ifPresent(equipo::setOficina);
            }
            return equipoRepositorio.save(equipo);
        } else {
            return null;
        }
    }

    /** Elimina un equipo. */
    public void eliminarEquipo(Long id) {
        equipoRepositorio.deleteById(id);
    }

    /** Cuenta los equipos. */
    public long contarEquipos() {
        return equipoRepositorio.count();
    }
}