package com.brenomorais.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.brenomorais.cobranca.model.StatusTitulo;
import com.brenomorais.cobranca.model.Titulo;
import com.brenomorais.cobranca.repository.TituloFilter;
import com.brenomorais.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class LancamentoController {

	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	//@Autowired
	//private Titulos titulos;
	
	@Autowired
	private CadastroTituloService cadastroTitulosService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
	
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try{
		
		cadastroTitulosService.salvar(titulo);
			
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");		
		//retornando a pagina em branco apos dados serem salvo
		return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	

	/*
	 * Retorna a pagina com todos os titulos
	 * 
	 * @RequestMapping("/titulos") no método pesquisar. Ou seja, se digitar no
	 * browser: localhost:8080/titulos irá chamar o método pesquisar() em
	 * TituloController.
	 */
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {		
		//Lista de titulos que fica disponivel na view de pesquisa titulos		
		//List<Titulo> todosTitulos = titulos.findAll(); lista todos os títulos
		
		List<Titulo> todosTitulos = cadastroTitulosService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos",todosTitulos);
		return mv;
	}	

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
	//Não pode ter dois requestmapping igual
	//Recebendo codigo do titulo para edicao
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo){
		
		//Titulo titulo = titulos.findOne(codigo);		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
		
	}	
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo,  RedirectAttributes attributes){
		
		cadastroTitulosService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");	
		return "redirect:/titulos";		
	}
	
	//Recebendo uma requisição via put e retornei, e atualiza o status do serviço, 
	//atualização e feita na classe de serviço
	@RequestMapping(value= "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){		
		System.out.println(" codigoTituloQuitação >>> "+codigo);
		return cadastroTitulosService.receber(codigo);
	}

}
