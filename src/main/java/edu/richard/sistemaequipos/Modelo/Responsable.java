package edu.richard.sistemaequipos.Modelo;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Schema(description = "Entidad Responsable")
@Entity
@Table(name = "responsables")
public class Responsable {

    @Schema(description = "ID del responsable", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del responsable", example = "Juan Pérez")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Apellido del responsable", example = "García")
    @Column(nullable = false)
    private String apellido;

    @Schema(description = "Cédula del responsable", example = "12345678")
    @Column(nullable = false)
    private String cedula;

    @Schema(description = "Email del responsable", example = "juan.perez@empresa.com")
    @Column(nullable = false, unique = true)
    private String email;

    @Schema(description = "Teléfono del responsable", example = "+1234567890")
    @Column(nullable = false)
    private String telefono;

    @Schema(description = "Cargo del responsable", example = "Jefe de TI")
    @Column(nullable = false)
    private String cargo;

    @Schema(description = "Unidad del responsable", example = "IT")
    @Column(nullable = false)
    private String unidad;

    @Schema(description = "Última fecha de modificación", example = "2024-06-25T10:00:00")
    @Column(name = "ultima_fecha_modificacion")
    private LocalDateTime ultimaFechaModificacion;

    @Schema(description = "Oficina a la que pertenece el responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id")
    @JsonBackReference
    private Oficina oficina;

    @Schema(description = "Equipos asignados al responsable")
    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Equipo> equipos = new ArrayList<>();

    public Responsable() {
    }

    public Responsable(Long id, @NonNull String nombre, @NonNull String apellido,
                       @NonNull String cedula, String email, String telefono, String cargo, String unidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.cargo = cargo;
        this.unidad = unidad;
    }

    public Responsable(@NonNull String nombre, @NonNull String apellido,
                       @NonNull String cedula, String email, String telefono, String cargo, String unidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.cargo = cargo;
        this.unidad = unidad;
    }

    @PrePersist
    protected void onCreate() {
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellido() {
        return apellido;
    }

    public void setApellido(@NonNull String apellido) {
        this.apellido = apellido;
    }

    @NonNull
    public String getCedula() {
        return cedula;
    }

    public void setCedula(@NonNull String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public LocalDateTime getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(LocalDateTime ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos != null ? equipos : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Responsable{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", cargo='" + cargo + '\'' +
                ", unidad='" + unidad + '\'' +
                ", ultimaFechaModificacion=" + ultimaFechaModificacion +
                ", oficina=" + (oficina != null ? oficina.getNombre() : "null") +
                '}';
    }
}