package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(
            value = "SELECT new com.devsuperior.dsmeta.dto.SaleDTO(obj.id,obj.amount,obj.date,obj.seller.name) " +
                    "FROM Sale obj " +
                    "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:vendedorNome,'%')) AND " +
                    "obj.date BETWEEN :minDate AND :maxDate"
    )
    Page<SaleDTO> relatorioDeVendas(String vendedorNome, LocalDate minDate, LocalDate maxDate, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "SELECT seller.name as sellerName,SUM(sale.amount) as total " +
                    "FROM tb_sales sale " +
                    "INNER JOIN tb_seller seller ON sale.seller_id = seller.id " +
                    "WHERE sale.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY sale.seller_id"
    )
    List<SaleSummaryProjection> sumarioDeVendas(LocalDate minDate,LocalDate maxDate);
}
