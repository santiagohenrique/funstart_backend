package com.funstart.funstartgames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funstart.funstartgames.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
