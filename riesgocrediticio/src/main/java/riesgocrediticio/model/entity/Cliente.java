package riesgocrediticio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clientes")
public abstract class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int puntajeCrediticio;

    @OneToMany
    @JoinColumn(name = "id_cliente")
    private List<Deuda> deudasActuales;

    @Column(nullable = false)
    private double montoSolicitado;

    @Column(nullable = false)
    private int plazoEnMeses;

    // Método abstracto para calcular capacidad de pago
    public abstract double calcularCapacidadPago();

    // Método para calcular total de deudas
    public double calcularTotalDeudas() {
        return deudasActuales.stream()
                .mapToDouble(Deuda::getMonto)
                .sum();
    }
}