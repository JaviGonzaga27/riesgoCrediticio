package riesgocrediticio.repository;

import riesgocrediticio.model.entity.HistorialEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialEvaluacionRepository extends JpaRepository<HistorialEvaluacion, Long> {

    // Buscar historial por ID del cliente
    List<HistorialEvaluacion> findByClienteId(Long clienteId);

    // Buscar historial ordenado por fecha (más reciente primero)
    List<HistorialEvaluacion> findByClienteIdOrderByFechaConsultaDesc(Long clienteId);
}