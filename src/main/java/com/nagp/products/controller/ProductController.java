package com.nagp.products.controller;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nagp.products.entity.PaginatedResult;
import com.nagp.products.entity.Product;
import com.nagp.products.model.ProductModel;
import com.nagp.products.repository.ProductRepository;
import com.nagp.products.service.FileService;
import com.nagp.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductRepository repo;

    @Autowired
    FileService fileService;

    @Autowired
    ProductService productService;
    
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        log.info("Received request for creating product {} ",product);
        return new ResponseEntity<>(repo.createProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/addImages/{id}")
    public ResponseEntity<String> addImages(@PathVariable("id") String productId, @RequestBody List<String> imageURIs) {
        log.info("Received request for adding image for user {}",productId);
        return new ResponseEntity<>(repo.addImageUri(productId, imageURIs), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") String productId) {
        log.info("Received request for getting product {} ",productId);
        return repo.getProductById(productId);
    }

    @GetMapping("/label/{label}")
    public List<Product> getProductByLabel(@PathVariable("label") String label) {
        log.info("Received request for fetching products by label {} ",label);
        return repo.getProductByLabel(label);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductByCategory(@PathVariable("category") String category) {
        log.info("Received request for fetching products by category {} ",category);
        return repo.getProductsByCategory(category);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        log.info("Received request to get all products");
        return repo.getAllProducts();
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<String>> uploadFiles(@PathVariable("id") String productId, @RequestParam("files") List<MultipartFile> files)  {
        log.info("Received request to upload images to s3");
        return new ResponseEntity<>(fileService.uploadFile(files, productId), HttpStatus.OK);
    }

    @GetMapping("/page")
    public PaginatedResult<Product> getAllProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Map<String, String> lastEvaluatedKey) {

        log.info("Received paginated request to get all products");
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
        log.info("Received search request for query {}", query);
        return productService.searchProducts(query);
    }

    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam String prefix) throws IOException {
        log.info("Received autocomplete request for prefix {}", prefix);
        return productService.autocomplete(prefix);
    }

    
}
