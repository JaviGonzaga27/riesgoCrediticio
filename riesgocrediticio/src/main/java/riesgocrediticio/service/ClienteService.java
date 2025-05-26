package riesgocrediticio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import riesgocrediticio.model.dto.ClienteRequestDTO;
import riesgocrediticio.model.dto.DeudaDTO;
import riesgocrediticio.model.entity.Cliente;
import riesgocrediticio.model.entity.Deuda;
import riesgocrediticio.model.entity.PersonaJuridica;
import riesgocrediticio.model.entity.PersonaNatural;
import riesgocrediticio.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crear(ClienteRequestDTO request) {
        Cliente cliente = convertirACliente(request);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, ClienteRequestDTO request) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }

        Cliente cliente = convertirACliente(request);
        cliente.setId(id); // Mantener el ID existente
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
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
}