package com.br.ufc.web.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.br.ufc.web.model.Pessoa;
import com.br.ufc.web.repository.PessoaRepository;

@Repository
@Transactional
public class UserDetailsServiceImplementacao implements UserDetailsService {
	
	@Autowired	
	private PessoaRepository pessoaRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Pessoa pessoa = pessoaRepository.findByLogin(login);
		
		if(pessoa == null) {
			throw new UsernameNotFoundException("Deu merda");
		}
		
		
		return new User(pessoa.getUsername(),pessoa.getPassword(),true,true,true,true,pessoa.getAuthorities());
	}

}
