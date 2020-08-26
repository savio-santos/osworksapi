package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.api.representationmodel.OrdemServicoInput;
import com.algaworks.osworks.api.representationmodel.OrdemServicoModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoRepository ordemServiceRepo;

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;

	// codigo boyler playte = codigo repetivo
	@Autowired
	private ModelMapper modelMapper; // mapeia objetos para DTO

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput input) {
	
		OrdemServico ordemServico= toEntity(input);
	
		return toModel(gestaoOrdemServico.criar(ordemServico));

	}// 

	@GetMapping
	public List<OrdemServicoModel> listar() {
		return toCollectionModel(ordemServiceRepo.findAll());
	}

	@GetMapping("/{ordemServiceId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServiceId) {
		Optional<OrdemServico> ordemServico = ordemServiceRepo.findById(ordemServiceId);
		if (ordemServico.isPresent()) {
			OrdemServicoModel model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizar(ordemServicoId);
	}
	
	
	
	
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}

	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(OrdemServico -> toModel(OrdemServico)).collect(Collectors.toList());
	}

	private OrdemServico toEntity(OrdemServicoInput ordemInput) {
		return modelMapper.map(ordemInput, OrdemServico.class);
	}
}
