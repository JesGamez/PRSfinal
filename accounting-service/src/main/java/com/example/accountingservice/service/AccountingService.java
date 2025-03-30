package com.example.accountingservice.service;

import com.example.accountingservice.exception.ResourceNotFoundException;
import com.example.accountingservice.model.Transaction;
import com.example.accountingservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountingService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Registrar una transacción (ingreso o gasto)
    public Transaction registerTransaction(Transaction transaction) {
        // Si no se define la fecha, se establece la fecha actual.
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }
        return transactionRepository.save(transaction);
    }

    // Obtener detalle de una transacción por ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    // Generar reporte financiero: total ingresos, total gastos y neto (ingresos - gastos)
    public Map<String, Object> generateReport() {
        List<Transaction> transactions = transactionRepository.findAll();

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();
        report.put("totalIncome", totalIncome);
        report.put("totalExpense", totalExpense);
        report.put("net", totalIncome.subtract(totalExpense));
        return report;
    }
}
