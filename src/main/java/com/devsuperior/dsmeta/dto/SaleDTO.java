package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public class SaleDTO extends SaleMinDTO{

    private String sellerName;

    public SaleDTO(Long id, Double amount, LocalDate date,String sellerName) {
        super(id, amount, date);
        this.sellerName = sellerName;
    }

    public SaleDTO(Sale entity) {
        super(entity);
        this.sellerName = entity.getSeller().getName();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
