package riesgocrediticio.model.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonaJuridica extends Cliente {
    private int antiguedadAnios;
    private double ingresoAnual;
    private int empleados;

    @Override
    public double calcularCapacidadPago() {
        return ingresoAnual * 0.35; // 35% del ingreso anual
    }

    // Método específico para personas jurídicas
    public double calcularRatioDeuda() {
        return calcularTotalDeudas() / ingresoAnual;
    }
}
