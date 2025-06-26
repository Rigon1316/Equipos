package edu.richard.sistemaequipos.Servicio;

import edu.richard.sistemaequipos.Modelo.Responsable;
import edu.richard.sistemaequipos.Repositorio.ResponsableRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Servicio para Responsable. */
@Service
public class ResponsableServicio {
    @Autowired
    private ResponsableRepositorio responsableRepositorio;

    /** Lista todos los responsables. */
    public List<Responsable> listarTodos() {
        return responsableRepositorio.findAll();
    }

    /** Busca un responsable por id. */
    public Optional<Responsable> buscarPorId(Long id) {
        return responsableRepositorio.findById(id);
    }

    /** Guarda un responsable. */
    public Responsable guardar(Responsable responsable) {
        return responsableRepositorio.save(responsable);
    }

    /** Actualiza un responsable. */
    public Responsable actualizarResponsable(Long id, Responsable responsableActualizado) {
        Optional<Responsable> optionalResponsable = responsableRepositorio.findById(id);
        if (optionalResponsable.isPresent()) {
            Responsable responsable = optionalResponsable.get();
            responsable.setNombre(responsableActualizado.getNombre());
            responsable.setApellido(responsableActualizado.getApellido());
            responsable.setCedula(responsableActualizado.getCedula());
            responsable.setEmail(responsableActualizado.getEmail());
            responsable.setTelefono(responsableActualizado.getTelefono());
            responsable.setCargo(responsableActualizado.getCargo());
            responsable.setUnidad(responsableActualizado.getUnidad());
            responsable.setOficina(responsableActualizado.getOficina());
            responsable.setUltimaFechaModificacion(responsableActualizado.getUltimaFechaModificacion());
            return responsableRepositorio.save(responsable);
        } else {
            return null;
        }
    }

    /** Elimina un responsable. */
    public void eliminar(Long id) {
        responsableRepositorio.deleteById(id);
    }
}
