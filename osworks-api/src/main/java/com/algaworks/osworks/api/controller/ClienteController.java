package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.ClienteServiceCadastro;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteServiceCadastro clienteService; 
	@Autowired
	private ClienteRepository clienteRepository;

	/* LISTAR TODOS */
	@GetMapping
	public List<Cliente> listar() {

		return clienteRepository.findAll();
		// return clienteRepository.findByNomeContaining("Jo");
		// return clienteRepository.findByNome

	}

	/* BUSCA POR ID */
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {

		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	/*---CREATE---*/
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)

	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
	}

	/*--UPDATE--*/
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId,@Valid @RequestBody Cliente cliente) {

		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = clienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	/* @Valid=> ativa o bean validation */

	/*--DELETE--*/
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {

		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		clienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}

}
