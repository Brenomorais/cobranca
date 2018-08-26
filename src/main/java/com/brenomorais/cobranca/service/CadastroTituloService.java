package com.brenomorais.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.brenomorais.cobranca.model.StatusTitulo;
import com.brenomorais.cobranca.model.Titulo;
import com.brenomorais.cobranca.repository.TituloFilter;
import com.brenomorais.cobranca.repository.Titulos;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}

	public void excluir(Long codigo){		
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		//recupera o titulo com o codigo recebido na requisição
		
		Titulo titulo = titulos.findOne(codigo); 	//Recebi o codigo
		titulo.setStatus(StatusTitulo.RECEBIDO);	//Atualizei o status
		titulos.save(titulo);						//salvei o titulo
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao(); //verificação para não passar null na linha abaixo		
		return titulos.findByDescricaoContaining(descricao); //consulta pela descricao dos titulos
		
		
		
	}

}
