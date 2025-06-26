package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Responsable;
import edu.richard.sistemaequipos.Servicio.ResponsableServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/responsables")
public class ResponsableControlador {
    
    @Autowired
    private ResponsableServicio responsableServicio;

    @GetMapping
    public ResponseEntity<List<Responsable>> listarTodos() {
        try {
            List<Responsable> responsables = responsableServicio.listarTodos();
            return ResponseEntity.ok(responsables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responsable> obtenerPorId(@PathVariable Long id) {
        try {
            return responsableServicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Responsable> crear(@RequestBody Responsable responsable) {
        try {
            responsable.setUltimaFechaModificacion(LocalDateTime.now());
            Responsable creado = responsableServicio.guardar(responsable);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responsable> actualizar(@PathVariable Long id, @RequestBody Responsable responsable) {
        try {
            responsable.setUltimaFechaModificacion(LocalDateTime.now());
            Responsable actualizado = responsableServicio.actualizarResponsable(id, responsable);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
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
            responsableServicio.eliminar(id);
            return ResponseEntity.ok("Responsable eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el responsable");
        }
    }
} 