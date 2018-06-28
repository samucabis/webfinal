package com.br.ufc.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.br.ufc.web.model.Pessoa;
import com.br.ufc.web.model.Role;
import com.br.ufc.web.repository.PessoaRepository;


@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public void adicionarPessoa(Pessoa pessoa) {
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		
		Role role = new Role();
		role.setPapel("ROLE_USER");
		System.out.println(pessoa.getLogin());
		System.out.println(pessoa.getSenha());		
		pessoa.setRoles(Arrays.asList(role));
		pessoaRepository.save(pessoa);
	}


	public List<Pessoa> retornarTodasAsPessoas() {
		
		return pessoaRepository.findAll();
	}
	
	public Pessoa buscarPorId(Long id) {
		return pessoaRepository.getOne(id);
	}


	public void removerPessoa(Long id) {
		pessoaRepository.deleteById(id);
		
	}
	
}
