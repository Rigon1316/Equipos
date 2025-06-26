package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Oficina;
import edu.richard.sistemaequipos.Servicio.OficinaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaControlador {
    
    @Autowired
    private OficinaServicio oficinaServicio;

    @GetMapping
    public ResponseEntity<List<Oficina>> listarTodas() {
        try {
            List<Oficina> oficinas = oficinaServicio.listarTodas();
            return ResponseEntity.ok(oficinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oficina> obtenerPorId(@PathVariable Long id) {
        try {
            return oficinaServicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Oficina> crear(@RequestBody Oficina oficina) {
        try {
            Oficina creada = oficinaServicio.guardar(oficina);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oficina> actualizar(@PathVariable Long id, @RequestBody Oficina oficina) {
        try {
            Oficina actualizada = oficinaServicio.actualizarOficina(id, oficina);
            if (actualizada != null) {
                return ResponseEntity.ok(actualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            oficinaServicio.eliminar(id);
            return ResponseEntity.ok("Oficina eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la oficina");
        }
    }
}
