package com.nagp.products.service;

import com.nagp.products.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private OpenSearchRestService openSearchService;

    /*public boolean createIndex() throws IOException {
        return openSearchService.createIndex();
    }

    public String addProduct(ProductModel product) throws IOException {
        return openSearchService.saveProduct(product);
    }*/

    public List<ProductModel> searchProducts(String query) throws IOException {
        return openSearchService.searchProducts(query);
    }

    /*public List<String> autocomplete(String prefix) throws IOException {
        return openSearchService.autocomplete(prefix);
    }*/
}
