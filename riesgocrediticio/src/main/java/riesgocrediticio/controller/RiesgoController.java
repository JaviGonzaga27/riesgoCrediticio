package riesgocrediticio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riesgocrediticio.model.dto.ClienteRequestDTO;
import riesgocrediticio.model.dto.EvaluacionResponseDTO;
import riesgocrediticio.service.RiesgoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RiesgoController {

    @Autowired
    private RiesgoService riesgoService;

    @PostMapping("/evaluar-riesgo")
    public ResponseEntity<EvaluacionResponseDTO> evaluarRiesgo(
            @RequestBody ClienteRequestDTO request) {

        try {
            EvaluacionResponseDTO response = riesgoService.evaluarRiesgo(request);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
