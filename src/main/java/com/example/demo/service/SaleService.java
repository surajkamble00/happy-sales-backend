package com.example.demo.service;

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