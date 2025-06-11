package edu.richard.sistemaequipos.Modelo;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String nombre;

    private String marca;

    private double precio;

    private LocalDate fecha;

    @Column(name = "ultima_fecha_modificacion")
    private LocalDateTime ultimaFechaModificacion;

    @Column(name = "departamento")
    private String departamento;

    // Constructor vacío
    public Equipo() {
    }

    // Constructor completo con id
    public Equipo(Integer id, @NonNull String nombre, String marca, double precio,
                  LocalDate fecha, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.fecha = fecha;
        this.departamento = departamento;
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    // Constructor sin id
    public Equipo(@NonNull String nombre, String marca, double precio,
                  LocalDate fecha, String departamento) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.fecha = fecha;
        this.departamento = departamento;
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    // Método que se ejecuta antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    //Método que se ejecuta antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        this.ultimaFechaModificacion = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(LocalDateTime ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", ultimaFechaModificacion=" + ultimaFechaModificacion +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}