package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Oficina;
import edu.richard.sistemaequipos.Servicio.OficinaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaControlador {
    @Autowired
    private OficinaServicio oficinaServicio;

    @GetMapping
    public List<Oficina> listarTodas() {
        return oficinaServicio.listarTodas();
    }

    @GetMapping("/{id}")
    public Oficina obtenerPorId(@PathVariable Long id) {
        return oficinaServicio.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Oficina crear(@RequestBody Oficina oficina) {
        return oficinaServicio.guardar(oficina);
    }

    @PutMapping("/{id}")
    public Oficina actualizar(@PathVariable Long id, @RequestBody Oficina oficina) {
        return oficinaServicio.actualizarOficina(id, oficina);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        oficinaServicio.eliminar(id);
        return "Oficina eliminada correctamente";
    }
}
