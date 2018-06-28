package com.br.ufc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.ufc.web.model.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}