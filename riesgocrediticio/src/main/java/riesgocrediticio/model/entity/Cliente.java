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
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "puntaje_crediticio", nullable = false)
    private int puntajeCrediticio;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Deuda> deudasActuales;

    @Column(name = "monto_solicitado", nullable = false)
    private double montoSolicitado;

    @Column(name = "plazo_en_meses", nullable = false)
    private int plazoEnMeses;

    // Métodos abstractos requeridos
    public abstract double getIngresoReferencial();
    public abstract boolean esAptoParaCredito();

    // Método para calcular total de deudas
    public double calcularTotalDeudas() {
        if (deudasActuales == null || deudasActuales.isEmpty()) {
            return 0.0;
        }
        return deudasActuales.stream()
                .mapToDouble(Deuda::getMonto)
                .sum();
    }

    public abstract double calcularCapacidadPago();