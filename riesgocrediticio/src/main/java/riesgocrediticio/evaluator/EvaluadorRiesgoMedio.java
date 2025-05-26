package riesgocrediticio.evaluator;

import org.springframework.stereotype.Component;
import riesgocrediticio.model.entity.Cliente;
import riesgocrediticio.model.entity.ResultadoEvaluacion;

@Component
public class EvaluadorRiesgoMedio extends EvaluadorRiesgo {

    @Override
    public boolean aplicaCliente(Cliente cliente) {
        int puntajeFinal = calcularPuntajeFinal(cliente);
        return puntajeFinal >= 60 && puntajeFinal < 80;
    }

    @Override
    public ResultadoEvaluacion evaluar(Cliente cliente) {
        int puntajeFinal = calcularPuntajeFinal(cliente);
        String nivelRiesgo = determinarNivelRiesgo(puntajeFinal);
        boolean aprobado = esClienteApto(puntajeFinal);

        ResultadoEvaluacion resultado = new ResultadoEvaluacion();
        resultado.setNivelRiesgo(nivelRiesgo);
        resultado.setAprobado(aprobado);
        resultado.setPuntajeFinal(puntajeFinal);
        resultado.setMensaje(generarMensaje(nivelRiesgo, aprobado));
        resultado.setTasaInteres(calcularTasaInteres(nivelRiesgo, aprobado));

        // Para riesgo medio, se puede ajustar el plazo
        int plazoAjustado = ajustarPlazoSegunRiesgo(cliente.getPlazoEnMeses(), puntajeFinal);
        resultado.setPlazoAprobado(aprobado ? plazoAjustado : 0);

        return resultado;
    }

    /**
     * Ajusta el plazo según el nivel de riesgo medio
     */
    private int ajustarPlazoSegunRiesgo(int plazoSolicitado, int puntaje) {
        // Si el puntaje está en el límite inferior del riesgo medio,
        // se puede reducir el plazo como medida de precaución
        if (puntaje < 70) {
            return Math.min(plazoSolicitado, 24); // Máximo 24 meses
        }
        return plazoSolicitado; // Mantener el plazo solicitado
    }
}