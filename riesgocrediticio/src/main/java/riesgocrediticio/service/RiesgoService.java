package riesgocrediticio.service;

import riesgocrediticio.evaluator.EvaluadorRiesgo;
import riesgocrediticio.model.dto.ClienteRequestDTO;
import riesgocrediticio.model.dto.DeudaDTO;
import riesgocrediticio.model.dto.EvaluacionResponseDTO;
import riesgocrediticio.model.entity.*;
import riesgocrediticio.repository.ClienteRepository;
import riesgocrediticio.repository.ResultadoEvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiesgoService {

    @Autowired
    private List<EvaluadorRiesgo> evaluadores;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ResultadoEvaluacionRepository resultadoRepository;

    @Autowired
    private HistorialService historialService;

    public EvaluacionResponseDTO evaluarRiesgo(ClienteRequestDTO request) {
        // 1. Convertir DTO a Entity
        Cliente cliente = convertirACliente(request);

        // 2. Guardar cliente
        cliente = clienteRepository.save(cliente);

        // 3. Seleccionar evaluador dinámicamente usando Streams
        EvaluadorRiesgo evaluador = evaluadores.stream()
                .filter(e -> e.aplicaCliente(cliente))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró evaluador apropiado"));

        // 4. Evaluar riesgo
        ResultadoEvaluacion resultado = evaluador.evaluar(cliente);

        // 5. Guardar resultado
        resultado = resultadoRepository.save(resultado);

        // 6. Registrar en historial
        historialService.registrarEvaluacion(cliente, resultado);

        // 7. Convertir a DTO y retornar
        return convertirAResponseDTO(resultado);
    }

    private Cliente convertirACliente(ClienteRequestDTO request) {
        Cliente cliente;

        if ("NATURAL".equals(request.getTipoCliente())) {
            PersonaNatural personaNatural = new PersonaNatural();
            personaNatural.setEdad(request.getEdad());
            personaNatural.setIngresoMensual(request.getIngresoMensual());
            cliente = personaNatural;
        } else if ("JURIDICA".equals(request.getTipoCliente())) {
            PersonaJuridica personaJuridica = new PersonaJuridica();
            personaJuridica.setAntiguedadAnios(request.getAntiguedadAnios());
            personaJuridica.setIngresoAnual(request.getIngresoAnual());
            personaJuridica.setEmpleados(request.getEmpleados());
            cliente = personaJuridica;
        } else {
            throw new IllegalArgumentException("Tipo de cliente no válido: " + request.getTipoCliente());
        }

        // Propiedades comunes
        cliente.setNombre(request.getNombre());
        cliente.setPuntajeCrediticio(request.getPuntajeCrediticio());
        cliente.setMontoSolicitado(request.getMontoSolicitado());
        cliente.setPlazoEnMeses(request.getPlazoEnMeses());

        // Convertir deudas usando Streams
        List<Deuda> deudas = request.getDeudasActuales().stream()
                .map(this::convertirADeuda)
                .collect(Collectors.toList());
        cliente.setDeudasActuales(deudas);

        return cliente;
    }

    private Deuda convertirADeuda(DeudaDTO deudaDTO) {
        Deuda deuda = new Deuda();
        deuda.setMonto(deudaDTO.getMonto());
        deuda.setPlazoMeses(deudaDTO.getPlazoMeses());
        return deuda;
    }

    private EvaluacionResponseDTO convertirAResponseDTO(ResultadoEvaluacion resultado) {
        EvaluacionResponseDTO response = new EvaluacionResponseDTO();
        response.setNivelRiesgo(resultado.getNivelRiesgo());
        response.setAprobado(resultado.isAprobado());
        response.setPuntajeFinal(resultado.getPuntajeFinal());
        response.setMensaje(resultado.getMensaje());
        response.setTasaInteres(resultado.getTasaInteres());
        response.setPlazoAprobado(resultado.getPlazoAprobado());
        return response;
    }
}