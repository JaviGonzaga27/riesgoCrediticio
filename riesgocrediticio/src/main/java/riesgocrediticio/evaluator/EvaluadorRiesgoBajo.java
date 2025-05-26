package riesgocrediticio.evaluator;

import org.springframework.stereotype.Component;
import riesgocrediticio.model.entity.Cliente;
import riesgocrediticio.model.entity.ResultadoEvaluacion;

@Component
public class EvaluadorRiesgoBajo extends EvaluadorRiesgo {

    @Override
    public boolean aplicaCliente(Cliente cliente) {
        int puntajeFinal = calcularPuntajeFinal(cliente);
        return puntajeFinal >= 80;
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
        resultado.setPlazoAprobado(aprobado ? cliente.getPlazoEnMeses() : 0);

        return resultado;
    }
}