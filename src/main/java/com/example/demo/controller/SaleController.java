package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaymentStatusUpdateRequest;
import com.example.demo.model.Sale;
import com.example.demo.service.SaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{invoiceNo}")
    public ResponseEntity<Sale> getSaleByInvoiceNo(@PathVariable String invoiceNo) {
        return ResponseEntity.ok(saleService.getSaleByInvoiceNo(invoiceNo));
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.createSale(sale));
    }

    @PatchMapping("/{invoiceNo}/payment-status")
    public ResponseEntity<Sale> updatePaymentStatus(
            @PathVariable String invoiceNo,
            @Valid @RequestBody PaymentStatusUpdateRequest request) {
        return ResponseEntity.ok(saleService.updatePaymentStatus(invoiceNo, request.getStatus()));
    }

    @DeleteMapping("/{invoiceNo}")
    public ResponseEntity<Void> deleteSale(@PathVariable String invoiceNo) {
        saleService.deleteSale(invoiceNo);
        return ResponseEntity.noContent().build();
    }
}