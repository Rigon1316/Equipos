package edu.richard.sistemaequipos.Modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Schema(description = "Entidad Equipo")
@Entity
@Table(name = "equipos")
public class Equipo {

    @Schema(description = "ID del equipo", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del equipo", example = "Laptop HP")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Marca del equipo", example = "HP")
    @Column(nullable = false)
    private String marca;

    @Schema(description = "Modelo del equipo", example = "EliteBook 840")
    @Column(nullable = false)
    private String modelo;

    @Schema(description = "Precio del equipo", example = "1200.0")
    @Column(nullable = false)
    private double precio;

    @Schema(description = "Fecha de adquisición", example = "2024-06-01")
    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;

    @Schema(description = "Última fecha de modificación", example = "2024-06-25")
    @Column(name = "ultima_fecha_modificacion")
    private LocalDate ultimaFechaModificacion;

    @Schema(description = "Departamento al que pertenece el equipo", example = "TI")
    @Column(nullable = false)
    private String departamento;

    @Schema(description = "Estado del equipo", example = "ACTIVO")
    @Column(nullable = false)
    private String estado;

    @Schema(description = "Responsable del equipo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    @JsonBackReference
    private Responsable responsable;

    @Schema(description = "Oficina donde se encuentra el equipo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id")
    @JsonBackReference
    private Oficina oficina;

    public Equipo() {
    }

    public Equipo(String nombre, String marca, String modelo, double precio, LocalDate fechaAdquisicion, String departamento, String estado) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaAdquisicion = fechaAdquisicion;
        this.departamento = departamento;
        this.estado = estado;
    }

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public LocalDate getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(LocalDate ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    @PrePersist
    protected void onCreate() {
        this.ultimaFechaModificacion = LocalDate.now();
        if (this.fechaAdquisicion == null) {
            this.fechaAdquisicion = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaFechaModificacion = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", ultimaFechaModificacion=" + ultimaFechaModificacion +
                ", departamento='" + departamento + '\'' +
                ", estado='" + estado + '\'' +
                ", responsable=" + (responsable != null ? responsable.getNombre() : "null") +
                ", oficina=" + (oficina != null ? oficina.getNombre() : "null") +
                '}';
    }
}
