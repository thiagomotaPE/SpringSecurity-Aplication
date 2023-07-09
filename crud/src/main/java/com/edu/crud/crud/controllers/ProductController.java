package com.edu.crud.crud.controllers;

import com.edu.crud.crud.domain.product.Product;
import com.edu.crud.crud.domain.product.ProductRepository;
import com.edu.crud.crud.domain.product.RequestProduct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @GetMapping
    public ResponseEntity getAllProducts() {
        var allProducts = repository.findALlByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data) {
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data) {
        Optional<Product> optionalProduct = repository.findById(data.id());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            product.setActive(data.active());
            return ResponseEntity.ok(product);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removeProduct(@PathVariable String id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setActive(false);
            return ResponseEntity.noContent().build();
        }else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/remove-forever/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
