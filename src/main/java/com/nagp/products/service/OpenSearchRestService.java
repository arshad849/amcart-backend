package com.nagp.products.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.products.model.ProductModel;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenSearchRestService {
    @Autowired
    private RestHighLevelClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /*public SearchResponse searchProducts(String query) throws Exception {
        SearchRequest searchRequest = new SearchRequest("products"); // Index name

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", query).fuzziness("AUTO")); // Fuzzy search

        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }*/

    public List<ProductModel> searchProducts(String query) throws IOException {
        //String searchJson = "{ \"query\": { \"multi_match\": { \"query\": \"" + query + "\", \"fields\": [\"name\", \"description\"] } } }";
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "name", "description");

        // Build the search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        SearchRequest request = new SearchRequest("products");
        //request.source().query(searchJson);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<ProductModel> products = new ArrayList<>();
        response.getHits().forEach(hit -> {
            try {
                products.add(objectMapper.readValue(hit.getSourceAsString(), ProductModel.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return products;
    }

    public List<String> autocomplete(String prefix) throws IOException {
        //String searchJson = "{ \"suggest\": { \"product_suggest\": { \"prefix\": \"" + prefix + "\", \"completion\": { \"field\": \"name\" } } } }";

        MatchPhrasePrefixQueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("name", prefix);

        // Build search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        SearchRequest request = new SearchRequest("products");
        request.source(searchSourceBuilder);

        // Execute search
        //SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<String> suggestions = new ArrayList<>();
        response.getHits().forEach(hit -> {
            suggestions.add(hit.getSourceAsMap().get("name").toString());
        });

        return suggestions;
    }
}
