package com.br.ufc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.ufc.web.model.Produto;


public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
