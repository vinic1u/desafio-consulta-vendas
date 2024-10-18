package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleDTO>> getReport(
			@RequestParam(name = "name",required = false,defaultValue = "") String nomeVendedor,
			@RequestParam(name = "minDate",required = false) String minDate,
			@RequestParam(name = "maxDate",required = false) String maxDate,
			Pageable pageable
	) {
		Page<SaleDTO> response = service.reportSales(nomeVendedor,minDate,maxDate,pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(
			@RequestParam(name = "minDate",required = false) String minDate,
			@RequestParam(name = "maxDate",required = false) String maxDate
	) {
		List<SaleSummaryProjection> response = service.summarySales(minDate,maxDate);
		return ResponseEntity.ok(response);
	}
}
