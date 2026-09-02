package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Sale;
import com.example.demo.model.SalePaymentStatus;
import com.example.demo.repository.SaleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleByInvoiceNo(String invoiceNo) {
        return saleRepository.findByInvoiceNo(invoiceNo)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found: " + invoiceNo));
    }

    @Transactional
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public Sale updateSale(String invoiceNo, Sale updatedData) {
        Sale existing = getSaleByInvoiceNo(invoiceNo);

        existing.setClientName(updatedData.getClientName());
        existing.setClientContact(updatedData.getClientContact());
        existing.setQuantityKg(updatedData.getQuantityKg());
        existing.setSellingPricePerKg(updatedData.getSellingPricePerKg());
        existing.setSaleDate(updatedData.getSaleDate());

        if (updatedData.getPaymentStatus() != null) {
            existing.setPaymentStatus(updatedData.getPaymentStatus());
        }

        // Recalculate total amount = quantityKg * sellingPricePerKg
        if (updatedData.getQuantityKg() != null && updatedData.getSellingPricePerKg() != null) {
            BigDecimal total = updatedData.getSellingPricePerKg().multiply(BigDecimal.valueOf(updatedData.getQuantityKg()));
            existing.setTotalAmount(total);
        } else if (updatedData.getTotalAmount() != null) {
            existing.setTotalAmount(updatedData.getTotalAmount());
        }

        return saleRepository.save(existing);
    }

    @Transactional
    public Sale updatePaymentStatus(String invoiceNo, String newStatus) {
        Sale sale = getSaleByInvoiceNo(invoiceNo);
        SalePaymentStatus status = SalePaymentStatus.valueOf(newStatus.toUpperCase());
        sale.setPaymentStatus(status);
        return saleRepository.save(sale);
    }

    @Transactional
    public void deleteSale(String invoiceNo) {
        Sale sale = getSaleByInvoiceNo(invoiceNo);
        saleRepository.delete(sale);
    }
}