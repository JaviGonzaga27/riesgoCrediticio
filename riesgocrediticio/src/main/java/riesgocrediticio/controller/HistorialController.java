package riesgocrediticio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riesgocrediticio.model.dto.HistorialDTO;
import riesgocrediticio.service.HistorialService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping("/historial/{clienteId}")
    public ResponseEntity<List<HistorialDTO>> obtenerHistorial(
            @PathVariable Long clienteId) {

        try {
            List<HistorialDTO> historial = historialService.obtenerHistorialPorCliente(clienteId);

            if (historial.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(historial, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
