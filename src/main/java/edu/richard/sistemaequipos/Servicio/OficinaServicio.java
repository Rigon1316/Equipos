package edu.richard.sistemaequipos.Servicio;

import edu.richard.sistemaequipos.Modelo.Oficina;
import edu.richard.sistemaequipos.Repositorio.OficinaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Servicio para Oficina. */
@Service
public class OficinaServicio {
    @Autowired
    private OficinaRepositorio oficinaRepositorio;

    /** Lista todas las oficinas. */
    public List<Oficina> listarTodas() {
        return oficinaRepositorio.findAll();
    }

    /** Busca una oficina por id. */
    public Optional<Oficina> buscarPorId(Long id) {
        return oficinaRepositorio.findById(id);
    }

    /** Guarda una oficina. */
    public Oficina guardar(Oficina oficina) {
        return oficinaRepositorio.save(oficina);
    }

    /** Actualiza una oficina. */
    public Oficina actualizarOficina(Long id, Oficina oficinaActualizada) {
        Optional<Oficina> optionalOficina = oficinaRepositorio.findById(id);
        if (optionalOficina.isPresent()) {
            Oficina oficina = optionalOficina.get();
            oficina.setNombre(oficinaActualizada.getNombre());
            oficina.setUbicacion(oficinaActualizada.getUbicacion());
            oficina.setUnidadAdministrativa(oficinaActualizada.getUnidadAdministrativa());
            return oficinaRepositorio.save(oficina);
        } else {
            return null;
        }
    }

    /** Elimina una oficina. */
    public void eliminar(Long id) {
        oficinaRepositorio.deleteById(id);
    }
}
