package riesgocrediticio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "historial_evaluacion")
public class HistorialEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cliente_nombre", nullable = false, length = 100)
    private String clienteNombre;

    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private String tipoCliente;

    @Column(name = "monto_solicitado", nullable = false)
    private double montoSolicitado;

    @Column(name = "plazo_en_meses", nullable = false)
    private int plazoEnMeses;

    @Column(name = "nivel_riesgo", nullable = false, length = 10)
    private String nivelRiesgo;

    @Column(name = "aprobado", nullable = false)
    private boolean aprobado;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;
}