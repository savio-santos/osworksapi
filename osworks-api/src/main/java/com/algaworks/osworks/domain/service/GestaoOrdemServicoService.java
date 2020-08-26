package com.algaworks.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.Comentario;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.model.StatusOrdemServico;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.repository.ComentarioRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServiceRepo;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServ) {
		Cliente cliente = clienteRepository.findById(ordemServ.getCliente().getId())
				.orElseThrow(()->new NegocioException("Cliente não encontrado")); 
		
		ordemServ.setCliente(cliente);
		ordemServ.setStatus(StatusOrdemServico.ABERTA);	
		
		ordemServ.setDataAbertura(OffsetDateTime.now());
		
		return ordemServiceRepo.save(ordemServ);
	}

	public List<OrdemServico> listar(){
		return ordemServiceRepo.findAll();
	} 
	
	public Comentario adicionarComentario(Long ordemServicoId,String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setOrdemServico(ordemServico);
		return comentarioRepository.save(comentario);
	}


	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico= buscar(ordemServicoId);
		
		
		ordemServico.finalizar();
		ordemServiceRepo.save(ordemServico);
	}

	
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServiceRepo.findById(ordemServicoId)
				.orElseThrow(()->new EntidadeNaoEncontradaException("Ordem de servico não encontrado"));
	}

	
}
