package com.br.ufc.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.ufc.web.model.Cart;
import com.br.ufc.web.model.Pessoa;
import com.br.ufc.web.model.Produto;
import com.br.ufc.web.repository.PessoaRepository;
import com.br.ufc.web.service.CartService;
import com.br.ufc.web.service.PessoaService;
import com.br.ufc.web.service.ProdutoService;


@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	List<Produto> produtos = new ArrayList<Produto>();
	double valor = 0;
	@Autowired
	private ProdutoService produtoService;
	private PessoaRepository pessoaRepository;
	private CartService carrinho;
	private Cart cart;
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping("/listar")
	public ModelAndView listarProdutos() {
		List<Produto> produtos = produtoService.retornarTodasOsProdutos();
		ModelAndView mv = new ModelAndView("todos-produtos");
		
		mv.addObject("todasOsProdutos", produtos);
		
		return mv;
	}
	
	@GetMapping("/carrinho")
	public ModelAndView carProdutos() {
		
		ModelAndView mv = new ModelAndView("cart");
		mv.addObject("listaTemporaria", produtos);
		mv.addObject("valorTotal", valor);
		return mv;
		
	}

	
	@RequestMapping("/formulario")
	public ModelAndView formularioProduto() {
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("produto", new Produto());
		return mv;
	}
	@PostMapping("/salvar")
	public ModelAndView salvarProduto(Produto produto , @RequestParam(value= "imagem") MultipartFile imagem) {
		produtoService.adicionarProduto(produto, imagem);
		
		ModelAndView mv = new ModelAndView("redirect:/produto/show");
		
		return mv;
	}
	
	@PostMapping("/salvarCliente")
	public ModelAndView salvarPessoa(Pessoa pessoa) {
		pessoaService.adicionarPessoa(pessoa);		
		ModelAndView mv = new ModelAndView("redirect:/");
		
		return mv;
	}
	
	
	
	@PostMapping("/salvarcart")
	public ModelAndView salvarCarrrinho() {
		Pessoa pessoa = pessoaRepository.findByLogin(SecurityContextHolder.getContext()
                .getAuthentication().getName());
		cart.setId_cliente(pessoa.getId());
		cart.setProdutos(produtos);
		cart.setValor(valor);
		carrinho.adicionarCarrinho(cart);
		
		ModelAndView mv = new ModelAndView("redirect:/listar");
		
		return mv;
	}
	
	@RequestMapping("/addcart/{id}")
	public ModelAndView addCart(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		//List<Pessoa> pessoas = new ArrayList<Pessoa>();
		produtos.add(produto);
		valor = produto.getValor() + valor;
		ModelAndView mv = new ModelAndView("redirect:/produto/carrinho");
		mv.addObject("listaTemporaria", produtos);
		mv.addObject("valorTotal", valor);
		return mv;
	}
	
	@RequestMapping("/produtoShow/{id}")
	public ModelAndView showProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("produto-desc");
		mv.addObject("produto", produto);
		return mv;
	}
	
	
	@RequestMapping("/cart")
	public ModelAndView listarCarrinho() {
		List<Produto> produtos = produtoService.retornarTodasOsProdutos();
		ModelAndView mv = new ModelAndView("todos-produtos");
		
		mv.addObject("listaTemporaria", produtos);
		mv.addObject("valorTotal", valor);
		
		return mv;
	}
	@RequestMapping("/cartexcluir/{id}")
	public ModelAndView excluirProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		
		for(Produto p : produtos) {
			if(p.getId() == produto.getId()) {
				valor = valor - p.getValor();
				produtos.remove(p);
				break;
			}
		}
		
		produtos.remove(produto);
		ModelAndView mv = new ModelAndView("redirect:/produto/carrinho");
		return mv;
	}
	
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirPessoa(@PathVariable Long id) {
		produtoService.removerProduto(id);
		ModelAndView mv = new ModelAndView("redirect:/produto/show");
		return mv;
	}
	
	@RequestMapping("/show")
	public ModelAndView show() {
	List<Produto> produtos = produtoService.retornarTodasOsProdutos();
	ModelAndView mv = new ModelAndView("show");
	
	mv.addObject("OsProdutos", produtos);
	return mv;
	}
	
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
	@RequestMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    
	    produtos.clear();
	    
	    return "redirect:/";
	
	}	
	
	@RequestMapping("{id}")
	public ModelAndView atualizarProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("produto", produto);
		
		return mv;
	}

}
