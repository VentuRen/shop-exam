package com.test.interview.services;

import com.test.interview.models.Product;
import com.test.interview.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 🔹 Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 🔹 Obtener un producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 🔹 Agregar un nuevo producto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 🔹 Actualizar un producto existente
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setStock(updatedProduct.getStock());
                    product.setImageUrl(updatedProduct.getImageUrl());
                    return productRepository.save(product);
                }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // 🔹 Eliminar un producto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
