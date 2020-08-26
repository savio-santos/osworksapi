package com.algaworks.osworks.api.representationmodel;

import javax.validation.constraints.NotNull;

public class ClienteIdInput {
	
	@NotNull
	private Long clienteId;

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
}
