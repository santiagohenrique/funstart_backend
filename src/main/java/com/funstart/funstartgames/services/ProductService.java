package com.funstart.funstartgames.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funstart.funstartgames.models.Product;
import com.funstart.funstartgames.repositories.ProductRepository;
import com.funstart.funstartgames.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll(){
		List<Product> list = repository.findAll();
		return list;
	}
	
	public Product findById(Long id) {
		Optional<Product> product = repository.findById(id);
		return product.orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
	}
	
	public Product insert(Product request) {
		Product newProduct = new Product();
		newProduct.setName(request.getName());
		newProduct.setPrice(request.getPrice());
		newProduct.setImgUrl(request.getImgUrl());
		newProduct.setSection(request.getSection());
		repository.save(newProduct);
		return newProduct;
	}
	
	public Product update(Long id, Product request) {
		Product existingProduct = verifyIfProductIdExists(id);
		
		existingProduct.setName(request.getName());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setImgUrl(request.getImgUrl());
		repository.save(existingProduct);
		return existingProduct;
	}
	
	public void delete(Long id) {
		verifyIfProductIdExists(id);
		repository.deleteById(id);
	}

	private Product verifyIfProductIdExists(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
	}
	
	
	
	
}
