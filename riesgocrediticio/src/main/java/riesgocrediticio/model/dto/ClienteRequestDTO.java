package riesgocrediticio.model.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.List;

@Getter
@Setter
public class ClienteRequestDTO {

    @NotBlank(message = "El tipo de cliente es obligatorio")
    private String tipoCliente; // "NATURAL" o "JURIDICA"

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Min(value = 300, message = "El puntaje crediticio mínimo es 300")
    @Max(value = 850, message = "El puntaje crediticio máximo es 850")
    private int puntajeCrediticio;

    @NotNull(message = "Las deudas actuales son obligatorias")
    private List<DeudaDTO> deudasActuales;

    @DecimalMin(value = "0.01", message = "El monto solicitado debe ser mayor a 0")
    private double montoSolicitado;

    @Min(value = 1, message = "El plazo mínimo es 1 mes")
    @Max(value = 120, message = "El plazo máximo es 120 meses")
    private int plazoEnMeses;

    // Campos específicos para PersonaNatural
    private Integer edad;
    private Double ingresoMensual;

    // Campos específicos para PersonaJuridica
    private Integer antiguedadAnios;
    private Double ingresoAnual;
    private Integer empleados;
}