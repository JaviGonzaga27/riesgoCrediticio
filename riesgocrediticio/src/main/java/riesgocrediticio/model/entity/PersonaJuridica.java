package riesgocrediticio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "personas_juridicas")
public class PersonaJuridica extends Cliente {

    @Column(name = "antiguedad_anios", nullable = false)
    private int antiguedadAnios;

    @Column(name = "ingreso_anual", nullable = false)
    private double ingresoAnual;

    @Column(name = "empleados", nullable = false)
    private int empleados;

    @Override
    public double getIngresoReferencial() {
        return ingresoAnual;
    }

    @Override
    public boolean esAptoParaCredito() {
        return antiguedadAnios >= 1;
    }

    @Override
    public double calcularCapacidadPago() {
        return ingresoAnual * 0.35;
    }

    public double calcularRatioDeuda() {
        return calcularTotalDeudas() / ingresoAnual;
    }

}