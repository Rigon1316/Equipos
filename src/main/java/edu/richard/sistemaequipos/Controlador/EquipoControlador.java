package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Equipo;
import edu.richard.sistemaequipos.Servicio.EquipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@CrossOrigin(origins = "*") // Permitir todos los orígenes para testing
@RestController
@RequestMapping("/api/equipo")
public class EquipoControlador {

    @Autowired
    private EquipoServicio equipoServicio;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosEquipos() {
        try {
            List<Equipo> equipos = equipoServicio.obtenerTodosLosEquipos();
            if (equipos.isEmpty()) {
                return ResponseEntity.ok().body("No hay equipos registrados.");
            }
            return ResponseEntity.ok(equipos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al obtener los equipos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEquipo(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("El ID debe ser un número positivo válido.");
            }

            Optional<Equipo> equipo = equipoServicio.obtenerEquipoPorId(id);
            if (equipo.isPresent()) {
                return ResponseEntity.ok(equipo.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Equipo con ID " + id + " no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al obtener el equipo: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearEquipo(@RequestBody Equipo nuevoEquipo) {
        try {
            if (nuevoEquipo == null) {
                return ResponseEntity.badRequest().body("Los datos del equipo son requeridos.");
            }

            if (nuevoEquipo.getNombre() == null || nuevoEquipo.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre del equipo es obligatorio.");
            }

            if (nuevoEquipo.getPrecio() < 0) {
                return ResponseEntity.badRequest().body("El precio no puede ser negativo.");
            }

            if (nuevoEquipo.getDepartamento() == null || nuevoEquipo.getDepartamento().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El departamento es obligatorio.");
            }

            if (nuevoEquipo.getFecha() == null) {
                nuevoEquipo.setFecha(LocalDate.now());
            }

            Equipo equipoGuardado = equipoServicio.guardarEquipo(nuevoEquipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(equipoGuardado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al crear el equipo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("El ID debe ser un número positivo válido.");
            }

            if (equipoActualizado == null) {
                return ResponseEntity.badRequest().body("Los datos del equipo son requeridos.");
            }

            if (equipoActualizado.getNombre() == null || equipoActualizado.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre del equipo es obligatorio.");
            }

            if (equipoActualizado.getPrecio() < 0) {
                return ResponseEntity.badRequest().body("El precio no puede ser negativo.");
            }

            if (equipoActualizado.getDepartamento() == null || equipoActualizado.getDepartamento().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El departamento es obligatorio.");
            }

            if (!equipoServicio.existeEquipo(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Equipo con ID " + id + " no encontrado.");
            }

            Equipo equipoActualizadoGuardado = equipoServicio.actualizarEquipo(id, equipoActualizado);
            return ResponseEntity.ok(equipoActualizadoGuardado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al actualizar el equipo: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("El ID debe ser un número positivo válido.");
            }

            equipoServicio.eliminarEquipo(id);
            return ResponseEntity.ok().body("Equipo eliminado exitosamente.");

        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al eliminar el equipo: " + e.getMessage());
        }
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<?> obtenerEquiposPorDepartamento(@PathVariable String departamento) {
        try {
            if (departamento == null || departamento.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El departamento no puede estar vacío.");
            }

            List<Equipo> equipos = equipoServicio.buscarEquiposPorDepartamento(departamento);

            if (equipos.isEmpty()) {
                return ResponseEntity.ok().body("No se encontraron equipos en el departamento: " + departamento);
            }

            return ResponseEntity.ok(equipos);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error interno del servidor al buscar equipos por departamento: " + e.getMessage());
        }
    }
}