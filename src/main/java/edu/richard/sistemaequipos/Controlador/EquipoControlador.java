package edu.richard.sistemaequipos.Controlador;

import edu.richard.sistemaequipos.Modelo.Equipo;
import edu.richard.sistemaequipos.Servicio.EquipoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Equipo", description = "Operaciones sobre equipos")
@RestController
@RequestMapping("/api/equipos")
public class EquipoControlador {

    @Autowired
    private EquipoServicio equipoServicio;

    @Operation(summary = "Lista todos los equipos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Equipo>> obtenerTodosLosEquipos() {
        try {
            List<Equipo> equipos = equipoServicio.obtenerTodosLosEquipos();
            return ResponseEntity.ok(equipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtiene un equipo por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerEquipo(@PathVariable Long id) {
        try {
            return equipoServicio.obtenerEquipoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Crea un nuevo equipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Equipo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo nuevoEquipo) {
        try {
            // Establecer fechas por defecto si no vienen
            if (nuevoEquipo.getFechaAdquisicion() == null) {
                nuevoEquipo.setFechaAdquisicion(LocalDate.now());
            }
            nuevoEquipo.setUltimaFechaModificacion(LocalDate.now());
            
            // Establecer estado por defecto si no viene
            if (nuevoEquipo.getEstado() == null) {
                nuevoEquipo.setEstado("ACTIVO");
            }
            
            Equipo creado = equipoServicio.guardarEquipo(nuevoEquipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualiza un equipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Equipo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        try {
            equipoActualizado.setUltimaFechaModificacion(LocalDate.now());
            Equipo actualizado = equipoServicio.actualizarEquipo(id, equipoActualizado);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Elimina un equipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Equipo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEquipo(@PathVariable Long id) {
        try {
            equipoServicio.eliminarEquipo(id);
            return ResponseEntity.ok("Equipo eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el equipo");
        }
    }
}
