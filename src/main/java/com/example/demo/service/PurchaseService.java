package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Purchase;
import com.example.demo.model.PurchasePaymentStatus;
import com.example.demo.repository.PurchaseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase record not found with id: " + id));
    }

    @Transactional
    public Purchase createPurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public Purchase updatePurchase(Long id, Purchase updatedData) {
        Purchase existing = getPurchaseById(id);

        existing.setFarmerName(updatedData.getFarmerName());
        existing.setCommodity(updatedData.getCommodity());
        existing.setRawQuantityKg(updatedData.getRawQuantityKg());
        existing.setRatePerKg(updatedData.getRatePerKg());
        existing.setTransportationCost(updatedData.getTransportationCost());
        existing.setPurchaseDate(updatedData.getPurchaseDate());

        if (updatedData.getPaymentStatus() != null) {
            existing.setPaymentStatus(updatedData.getPaymentStatus());
        }

        // Recalculate total landed cost = (rawQuantityKg * ratePerKg) + transportationCost
        if (updatedData.getRawQuantityKg() != null && updatedData.getRatePerKg() != null) {
            BigDecimal rawTotal = updatedData.getRatePerKg().multiply(BigDecimal.valueOf(updatedData.getRawQuantityKg()));
            BigDecimal transport = updatedData.getTransportationCost() != null ? updatedData.getTransportationCost() : BigDecimal.ZERO;
            existing.setTotalLandedCost(rawTotal.add(transport));
        } else if (updatedData.getTotalLandedCost() != null) {
            existing.setTotalLandedCost(updatedData.getTotalLandedCost());
        }

        return purchaseRepository.save(existing);
    }

    @Transactional
    public Purchase updatePaymentStatus(Long id, String newStatus) {
        Purchase purchase = getPurchaseById(id);
        PurchasePaymentStatus status = PurchasePaymentStatus.valueOf(newStatus.toUpperCase());
        purchase.setPaymentStatus(status);
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new EntityNotFoundException("Purchase record not found with id: " + id);
        }
        purchaseRepository.deleteById(id);
    }
}