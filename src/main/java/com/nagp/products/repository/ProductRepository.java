package com.nagp.products.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nagp.products.entity.PaginatedResult;
import com.nagp.products.entity.Product;
import com.nagp.products.service.CounterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Repository
@AllArgsConstructor
@Slf4j
public class ProductRepository {
    final private DynamoDBMapper dynamoDBMapper;
    private final CounterService counterService;

    public String createProduct(Product product){
        log.info("Saving Product {}", product);

        dynamoDBMapper.save(product);
        return product.getProductId();
    }

    public Product getProductById(String id){
        log.info("getting product for the id : {}", id);
        return dynamoDBMapper.load(Product.class, id);
    }

    public List<Product> getProductByLabel(String label){
        log.info("getting product for the label : {}", label);
        Product product = new Product();
        product.setCategory(label);

        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>()
                .withIndexName("label-index") // Ensure category is indexed
                .withConsistentRead(false)
                .withHashKeyValues(product);

        return dynamoDBMapper.query(Product.class, queryExpression);
    }

    public List<Product> getAllProducts() {
        log.info("Fetching all the products");
        return dynamoDBMapper.scan(Product.class, new DynamoDBScanExpression());
    }

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        log.info("Fetching products for category {}", category);
        Product product = new Product();
        product.setCategory(category);

        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>()
                .withIndexName("category-index") // Ensure category is indexed
                .withConsistentRead(false)
                .withHashKeyValues(product);

        return dynamoDBMapper.query(Product.class, queryExpression);
    }

    public Product updateProduct(String id, Product product){
        log.info("Product update request :: {}",product);
        Product ep = dynamoDBMapper.load(Product.class, id);
        ep.setUpdatedAt(LocalDateTime.now().toString());
        ep.setCategory(product.getCategory());
        ep.setAttributes(product.getAttributes());
        dynamoDBMapper.save(ep);
        return ep;
    }

    public String addImageUri(String id, List<String> imageArray){
        log.info("Adding image URI to product {}", id);
        Product ep = dynamoDBMapper.load(Product.class, id);
        ep.setImages(imageArray);
        dynamoDBMapper.save(ep);
        return ep.getProductId();
    }

    public String deleteProduct(String id){
        log.info("Deleting product : {}", id);
        Product ep = dynamoDBMapper.load(Product.class, id);
        dynamoDBMapper.delete(ep);
        return ep.getProductId();
    }

    public PaginatedResult<Product> getAllProducts(int limit, Map<String, AttributeValue> lastEvaluatedKey) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withLimit(limit);

        if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
            scanExpression.setExclusiveStartKey(lastEvaluatedKey);
        }

        PaginatedScanList<Product> scanResult = dynamoDBMapper.scan(Product.class, scanExpression);

        Map<String, AttributeValue> nextPageKey = scanExpression.getExclusiveStartKey();

        return new PaginatedResult<>(scanResult, nextPageKey);
    }
}
