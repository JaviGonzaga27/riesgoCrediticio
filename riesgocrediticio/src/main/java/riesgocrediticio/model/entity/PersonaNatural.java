package riesgocrediticio.model.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonaNatural extends Cliente {
    private int edad;
    private double ingresoMensual;

    @Override
    public double calcularCapacidadPago() {
        return ingresoMensual * 0.4; // 40% del ingreso mensual
    }

    // Método específico para personas naturales
    public double calcularRatioDeuda() {
        return calcularTotalDeudas() / ingresoMensual;
    }
}
