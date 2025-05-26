package riesgocrediticio.repository;

import riesgocrediticio.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar cliente por nombre
    Optional<Cliente> findByNombre(String nombre);

    // Verificar si existe un cliente por nombre
    boolean existsByNombre(String nombre);
}