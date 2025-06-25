package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Responsable;
import edu.richard.sistemaequipos.Servicio.ResponsableServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/responsables")
public class ResponsableControlador {
    @Autowired
    private ResponsableServicio responsableServicio;

    @GetMapping
    public List<Responsable> listarTodos() {
        return responsableServicio.listarTodos();
    }

    @GetMapping("/{id}")
    public Responsable obtenerPorId(@PathVariable Long id) {
        return responsableServicio.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Responsable crear(@RequestBody Responsable responsable) {
        responsable.setUltimaFechaModificacion(LocalDateTime.now());
        return responsableServicio.guardar(responsable);
    }

    @PutMapping("/{id}")
    public Responsable actualizar(@PathVariable Long id, @RequestBody Responsable responsable) {
        responsable.setUltimaFechaModificacion(LocalDateTime.now());
        return responsableServicio.actualizarResponsable(id, responsable);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        responsableServicio.eliminar(id);
        return "Responsable eliminado correctamente";
    }
} 