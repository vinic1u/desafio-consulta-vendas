package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


	@Transactional(readOnly = true)
	public Page<SaleDTO> reportSales(String nomeVendedor, String minDate, String maxDate, Pageable pageable){
		LocalDate dataAtual = LocalDate.ofInstant(Instant.now(),ZoneId.systemDefault());
		LocalDate minDataConvertida;
		LocalDate maxDataConvertida;

		if(minDate == null) {
			minDataConvertida = dataAtual.minusYears(1L);
		}else{
			minDataConvertida = LocalDate.parse(minDate);
		}

		if(maxDate == null) {
			maxDataConvertida = dataAtual;
		}else{
			maxDataConvertida = LocalDate.parse(maxDate);
		}

		Page<SaleDTO> pages = repository.relatorioDeVendas(nomeVendedor,minDataConvertida,maxDataConvertida,pageable);
		return pages;
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryProjection> summarySales(String minDate,String maxDate){
		LocalDate dataAtual = LocalDate.ofInstant(Instant.now(),ZoneId.systemDefault());
		LocalDate minDataConvertida;
		LocalDate maxDataConvertida;

		if(minDate == null) {
			minDataConvertida = dataAtual.minusYears(1L);
		}else{
			minDataConvertida = LocalDate.parse(minDate);
		}

		if(maxDate == null) {
			maxDataConvertida = dataAtual;
		}else{
			maxDataConvertida = LocalDate.parse(maxDate);
		}
		List<SaleSummaryProjection> summaryProjections = repository.sumarioDeVendas(minDataConvertida,maxDataConvertida);
		return summaryProjections;
	}
}
