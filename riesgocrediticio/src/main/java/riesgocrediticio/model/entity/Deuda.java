package riesgocrediticio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "deudas")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "monto", nullable = false)
    private double monto;

    @Column(name = "plazo_meses", nullable = false)
    private int plazoMeses;

    @Column(name = "cliente_id")
    private Long clienteId;
}