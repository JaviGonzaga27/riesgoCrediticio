package riesgocrediticio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resultado_evaluacion")
public class ResultadoEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nivel_riesgo", nullable = false, length = 10)
    private String nivelRiesgo;

    @Column(name = "aprobado", nullable = false)
    private boolean aprobado;

    @Column(name = "puntaje_final", nullable = false)
    private int puntajeFinal;

    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @Column(name = "tasa_interes", nullable = false)
    private double tasaInteres;

    @Column(name = "plazo_aprobado", nullable = false)
    private int plazoAprobado;
}