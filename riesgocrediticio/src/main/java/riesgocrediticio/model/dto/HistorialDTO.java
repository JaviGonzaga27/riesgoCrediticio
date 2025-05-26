package riesgocrediticio.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialDTO {
    private Long id;
    private String clienteNombre;
    private String tipoCliente;
    private double montoSolicitado;
    private int plazoEnMeses;
    private String nivelRiesgo;
    private boolean aprobado;
    private LocalDateTime fechaConsulta;
}