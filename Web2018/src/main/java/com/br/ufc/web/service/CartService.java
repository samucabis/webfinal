package com.br.ufc.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ufc.web.model.Cart;
import com.br.ufc.web.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository carrinhoRepository;
	
	public void adicionarCarrinho(Cart cart) {
		
		carrinhoRepository.save(cart);
	}
	

	public List<Cart> retornarTodasOsProdutos() {
		
		return carrinhoRepository.findAll();
	}
	
	public Cart buscarPorId(Long id) {
		return carrinhoRepository.getOne(id);
	}


	public void removerCart(Long id) {
		carrinhoRepository.deleteById(id);
		
	}
}
