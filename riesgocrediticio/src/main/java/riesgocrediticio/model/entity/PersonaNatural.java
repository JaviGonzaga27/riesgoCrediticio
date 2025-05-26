package riesgocrediticio.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "personas_naturales")
public class PersonaNatural extends Cliente {

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "ingreso_mensual", nullable = false)
    private double ingresoMensual;

    @Override
    public double getIngresoReferencial() {
        return ingresoMensual;
    }

    @Override
    public boolean esAptoParaCredito() {
        return edad >= 18;
    }

    @Override
    public double calcularCapacidadPago() {
        return ingresoMensual * 0.35;
    }

    public double calcularRatioDeuda() {
        return calcularTotalDeudas() / ingresoMensual;
    }
}