package com.example.accountingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Monto de la transacción
    @Column(nullable = false)
    private BigDecimal amount;

    // Descripción o detalle de la transacción
    @Column
    private String description;

    // Tipo de transacción: "INCOME" o "EXPENSE"
    @Column(nullable = false)
    private String type;

    // Fecha y hora de la transacción
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
}
