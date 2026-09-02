package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaymentStatusUpdateRequest;
import com.example.demo.model.Purchase;
import com.example.demo.service.PurchaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody Purchase purchase) {
        return ResponseEntity.ok(purchaseService.createPurchase(purchase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(
            @PathVariable Long id, 
            @RequestBody Purchase purchase) {
        return ResponseEntity.ok(purchaseService.updatePurchase(id, purchase));
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<Purchase> updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody PaymentStatusUpdateRequest request) {
        return ResponseEntity.ok(purchaseService.updatePaymentStatus(id, request.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}