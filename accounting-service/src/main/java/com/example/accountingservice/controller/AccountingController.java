package com.example.accountingservice.controller;

import com.example.accountingservice.model.Transaction;
import com.example.accountingservice.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounting")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    // POST /accounting/transactions – Registrar una transacción (ingreso o gasto)
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> registerTransaction(@RequestBody Transaction transaction) {
        Transaction created = accountingService.registerTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /accounting/transactions/{id} – Obtener detalle de una transacción
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = accountingService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    // GET /accounting/report – Generar un reporte financiero
    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> generateReport() {
        Map<String, Object> report = accountingService.generateReport();
        return ResponseEntity.ok(report);
    }
}
