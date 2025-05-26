package riesgocrediticio.evaluator;

import org.springframework.stereotype.Component;
import riesgocrediticio.model.entity.*;

@Component
public abstract class EvaluadorRiesgo {
    private final double puntajeBase = 100.0;

    /**
     * Determina si este evaluador puede procesar el cliente dado
     */
    public abstract boolean aplicaCliente(Cliente cliente);

    /**
     * Evalúa el riesgo del cliente y retorna el resultado
     */
    public abstract ResultadoEvaluacion evaluar(Cliente cliente);

    /**
     * Calcula el puntaje final aplicando todas las penalizaciones
     */
    public int calcularPuntajeFinal(Cliente cliente) {
        double puntaje = puntajeBase;

        //Penalizacion por puntaje crediticio bajo
        if (cliente.getPuntajeCrediticio() < 650) {
            puntaje -= 30;
        }

        //Penalizacion especificas por tipo de cliente
        if (cliente instanceof PersonaNatural) {
            puntaje = aplicarPenalizacionesPersonaNatural((PersonaNatural) cliente, puntaje);
        } else if (cliente instanceof PersonaJuridica) {
            puntaje = aplicarPenalizacionesPersonaJuridica((PersonaJuridica) cliente, puntaje);
        }
        return (int) Math.max(0, puntaje);
    }

    /**
     * Aplica penalizaciones específicas para persona natural
     */
    private double aplicarPenalizacionesPersonaNatural(PersonaNatural cliente, double puntaje) {
        double deudaTotal = calcularDeudaTotal(cliente);

        // Penalización por deudas > 40% del ingreso mensual
        if (deudaTotal > (cliente.getIngresoMensual() * 0.40)) {
            puntaje -= 15;
        }

        // Penalización por monto solicitado > 50% del ingreso mensual
        if (cliente.getMontoSolicitado() > (cliente.getIngresoMensual() * 0.50)) {
            puntaje -= 10;
        }

        return puntaje;
    }

    /**
     * Aplica penalizaciones específicas para persona jurídica
     */
    private double aplicarPenalizacionesPersonaJuridica(PersonaJuridica cliente, double puntaje) {
        double deudaTotal = calcularDeudaTotal(cliente);

        // Penalización por deudas > 35% del ingreso anual
        if (deudaTotal > (cliente.getIngresoAnual() * 0.35)) {
            puntaje -= 20;
        }

        // Penalización por monto solicitado > 30% del ingreso anual
        if (cliente.getMontoSolicitado() > (cliente.getIngresoAnual() * 0.30)) {
            puntaje -= 15;
        }

        return puntaje;
    }

    /**
     * Calcula el total de deudas del cliente
     */
    private double calcularDeudaTotal(Cliente cliente) {
        return cliente.getDeudasActuales().stream()
                .mapToDouble(Deuda::getMonto)
                .sum();
    }

    /**
     * Determina el nivel de riesgo basado en el puntaje
     */
    public String determinarNivelRiesgo(int puntaje) {
        if (puntaje >= 80) {
            return "BAJO";
        } else if (puntaje >= 60) {
            return "MEDIO";
        } else {
            return "ALTO";
        }
    }

    /**
     * Determina si el cliente es apto para el préstamo
     */
    public boolean esClienteApto(int puntaje) {
        return puntaje >= 60;
    }

    /**
     * Genera el mensaje según el nivel de riesgo
     */
    public String generarMensaje(String nivelRiesgo, boolean aprobado) {
        if (!aprobado) {
            return "Cliente no apto para préstamo";
        }

        switch (nivelRiesgo) {
            case "BAJO":
                return "Cliente apto para préstamo con condiciones preferenciales";
            case "MEDIO":
                return "Cliente apto para préstamo con condiciones ajustadas";
            default:
                return "Cliente evaluado";
        }
    }

    /**
     * Calcula la tasa de interés según el nivel de riesgo
     */
    public double calcularTasaInteres(String nivelRiesgo, boolean aprobado) {
        if (!aprobado) {
            return 0.0;
        }

        switch (nivelRiesgo) {
            case "BAJO":
                return 6.5;
            case "MEDIO":
                return 8.0;
            default:
                return 0.0;
        }
    }
}
