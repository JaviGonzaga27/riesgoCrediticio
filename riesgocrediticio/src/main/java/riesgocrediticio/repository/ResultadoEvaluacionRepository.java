package riesgocrediticio.repository;

import riesgocrediticio.model.entity.ResultadoEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoEvaluacionRepository extends JpaRepository<ResultadoEvaluacion, Long> {

    // Este repository es básico, solo para CRUD estándar
    // Los métodos heredados de JpaRepository son suficientes:
    // save(), findById(), findAll(), delete(), etc.
}