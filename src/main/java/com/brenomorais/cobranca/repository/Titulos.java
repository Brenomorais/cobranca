package com.brenomorais.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brenomorais.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long>{

	//Implmenta consulta de titulos pela descrição como estivesse usando o like em sql
	public List<Titulo> findByDescricaoContaining(String descricao);
	
}
