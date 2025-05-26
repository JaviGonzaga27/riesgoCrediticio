package riesgocrediticio.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluacionResponseDTO {
    private String nivelRiesgo;
    private boolean aprobado;
    private int puntajeFinal;
    private String mensaje;
    private double tasaInteres;
    private int plazoAprobado;
}