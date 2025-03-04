package com.nagp.products.controller;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nagp.products.entity.PaginatedResult;
import com.nagp.products.entity.Product;
import com.nagp.products.model.ProductModel;
import com.nagp.products.repository.ProductRepository;
import com.nagp.products.service.FileService;
import com.nagp.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    
    @Autowired
    ProductRepository repo;

    @Autowired
    FileService fileService;

    @Autowired
    ProductService productService;
    
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>(repo.createProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/addImages/{id}")
    public ResponseEntity<String> addImages(@PathVariable("id") String productId, @RequestBody List<String> imageURIs) {
        return new ResponseEntity<>(repo.addImageUri(productId, imageURIs), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") String productId) {
        System.out.println(productId);
        return repo.getProductById(productId);
    }

    @GetMapping("/label/{label}")
    public List<Product> getProductByLabel(@PathVariable("label") String label) {
        System.out.println(label);
        return repo.getProductByLabel(label);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductByCategory(@PathVariable("category") String category) {
        System.out.println(category);
        return repo.getProductsByCategory(category);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<String>> uploadFiles(@PathVariable("id") String productId, @RequestParam("files") List<MultipartFile> files)  {
        return new ResponseEntity<>(fileService.uploadFile(files, productId), HttpStatus.OK);
    }

    @GetMapping("/page")
    public PaginatedResult<Product> getAllProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Map<String, String> lastEvaluatedKey) {

        // Convert lastEvaluatedKey from String to Map<String, AttributeValue>
        Map<String, AttributeValue> parsedLastEvaluatedKey;
        if (lastEvaluatedKey != null) {
            parsedLastEvaluatedKey = new HashMap<>();
            lastEvaluatedKey.forEach((key, value) ->
                    parsedLastEvaluatedKey.put(key, new AttributeValue(value))
            );
        } else {
            parsedLastEvaluatedKey = null;
        }

        return repo.getAllProducts(limit, parsedLastEvaluatedKey);
    }

    @GetMapping("/search")
    public List<ProductModel> search(@RequestParam String query) throws IOException {
        return productService.searchProducts(query);
    }

    // ✅ Autocomplete Suggestion
    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam String prefix) throws IOException {
        return productService.autocomplete(prefix);
    }

    
}
