package edu.richard.sistemaequipos.Modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidad Oficina")
@Entity
@Table(name = "oficinas")
public class Oficina {

    @Schema(description = "ID de la oficina", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre de la oficina", example = "Oficina Central")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Ubicación de la oficina", example = "Edificio A, Piso 2")
    @Column(nullable = false)
    private String ubicacion;

    @Schema(description = "Unidad administrativa", example = "Administración")
    @Column(nullable = false)
    private String unidadAdministrativa;

    // Relación One-to-Many con Equipo (relación inversa)
    @Schema(description = "Equipos en la oficina")
    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipo> equipos = new ArrayList<>();

    @Schema(description = "Responsables en la oficina")
    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Responsable> responsables = new ArrayList<>();

    // Constructor vacío (requerido por JPA)
    public Oficina() {
    }

    // Constructor sin ID (para nuevas entidades)
    public Oficina(String nombre, String ubicacion, String unidadAdministrativa) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.unidadAdministrativa = unidadAdministrativa;
    }

    // Constructor completo (para casos específicos)
    public Oficina(Long id, String nombre, String ubicacion, String unidadAdministrativa) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.unidadAdministrativa = unidadAdministrativa;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

    // Métodos de conveniencia para manejo de relaciones
    public void addEquipo(Equipo equipo) {
        if (equipo != null) {
            this.equipos.add(equipo);
            equipo.setOficina(this);
        }
    }

    public void removeEquipo(Equipo equipo) {
        if (equipo != null) {
            this.equipos.remove(equipo);
            equipo.setOficina(null);
        }
    }

    public void addResponsable(Responsable responsable) {
        if (responsable != null) {
            this.responsables.add(responsable);
            responsable.setOficina(this);
        }
    }

    public void removeResponsable(Responsable responsable) {
        if (responsable != null) {
            this.responsables.remove(responsable);
            responsable.setOficina(null);
        }
    }

    // equals y hashCode (importantes para JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oficina oficina = (Oficina) o;
        return Objects.equals(id, oficina.id) &&
                Objects.equals(nombre, oficina.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    // toString mejorado (evita lazy loading issues)
    @Override
    public String toString() {
        return "Oficina{" + "id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", unidadAdministrativa=" + unidadAdministrativa + '}';
    }
}