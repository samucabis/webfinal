package com.br.ufc.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.ufc.web.model.Pessoa;
import com.br.ufc.web.service.PessoaService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;

	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	@RequestMapping(value="/formulario2", method=RequestMethod.GET)
	public ModelAndView formularioCliente() {
		ModelAndView mv = new ModelAndView("formulario2");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}
	@RequestMapping(value="/salvarCliente", method=RequestMethod.POST)
	public ModelAndView salvarPessoa(Pessoa pessoa) {
		System.out.println(pessoa.getCpf());
		System.out.println(pessoa.getNome());
		System.out.println(pessoa.getData());
		System.out.println(pessoa.getEndereco());
		System.out.println(pessoa.getLogin());
		System.out.println(pessoa.getSenha());
		pessoaService.adicionarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;		
	}
	
	
}
