package com.algaworks.osworks.api.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //componente spring com o objetico de configuração de bean
public class ModelapperConfig {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
