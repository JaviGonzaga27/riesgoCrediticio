package riesgocrediticio.service;

import riesgocrediticio.model.dto.HistorialDTO;
import riesgocrediticio.model.entity.Cliente;
import riesgocrediticio.model.entity.HistorialEvaluacion;
import riesgocrediticio.model.entity.PersonaNatural;
import riesgocrediticio.model.entity.ResultadoEvaluacion;
import riesgocrediticio.repository.HistorialEvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialService {

    @Autowired
    private HistorialEvaluacionRepository historialRepository;

    public void registrarEvaluacion(Cliente cliente, ResultadoEvaluacion resultado) {
        HistorialEvaluacion historial = new HistorialEvaluacion();

        historial.setCliente(cliente);
        historial.setClienteNombre(cliente.getNombre());
        historial.setTipoCliente(cliente instanceof PersonaNatural ? "NATURAL" : "JURIDICA");
        historial.setMontoSolicitado(cliente.getMontoSolicitado());
        historial.setPlazoEnMeses(cliente.getPlazoEnMeses());
        historial.setNivelRiesgo(resultado.getNivelRiesgo());
        historial.setAprobado(resultado.isAprobado());
        historial.setFechaConsulta(LocalDateTime.now());

        historialRepository.save(historial);
    }

    public List<HistorialDTO> obtenerHistorialPorCliente(Long clienteId) {
        return historialRepository.findByClienteIdOrderByFechaConsultaDesc(clienteId)
                .stream()
                .map(this::convertirAHistorialDTO)
                .collect(Collectors.toList());
    }

    private HistorialDTO convertirAHistorialDTO(HistorialEvaluacion historial) {
        HistorialDTO dto = new HistorialDTO();
        dto.setId(historial.getId());
        dto.setClienteNombre(historial.getClienteNombre());
        dto.setTipoCliente(historial.getTipoCliente());
        dto.setMontoSolicitado(historial.getMontoSolicitado());
        dto.setPlazoEnMeses(historial.getPlazoEnMeses());
        dto.setNivelRiesgo(historial.getNivelRiesgo());
        dto.setAprobado(historial.isAprobado());
        dto.setFechaConsulta(historial.getFechaConsulta());
        return dto;
    }
}