package riesgocrediticio.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteRequestDTO {

    private String tipoCliente; // "NATURAL" o "JURIDICA"

    private String nombre;
    private int puntajeCrediticio;

    private List<DeudaDTO> deudasActuales;
    private double montoSolicitado;
    private int plazoEnMeses;

    // Campos específicos para PersonaNatural
    private Integer edad;
    private Double ingresoMensual;

    // Campos específicos para PersonaJuridica
    private Integer antiguedadAnios;
    private Double ingresoAnual;
    private Integer empleados;
}