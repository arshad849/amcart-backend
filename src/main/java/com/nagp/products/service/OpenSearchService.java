package com.nagp.products.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.products.entity.Product;
import com.nagp.products.model.ProductModel;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.CreateIndexResponse;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.opensearch.index.query.MultiMatchQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenSearchService {
    @Autowired
    private RestHighLevelClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String INDEX_NAME = "products";

    // ✅ Create Index
    public boolean createIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        if (!exists) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        }
        return true;
    }

    // ✅ Store Product in OpenSearch
    public String saveProduct(ProductModel product) throws IOException {
        IndexRequest request = new IndexRequest(INDEX_NAME)
                .id(product.getProductId())
                .source(objectMapper.writeValueAsString(product), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.getId();
    }

    // ✅ Search Product by Name or Description (Full-Text Search)
    public List<ProductModel> searchProducts(String query) throws IOException {
        //String searchJson = "{ \"query\": { \"multi_match\": { \"query\": \"" + query + "\", \"fields\": [\"name\", \"description\"] } } }";
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "name", "description");

        // Build the search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        SearchRequest request = new SearchRequest(INDEX_NAME);
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

    // ✅ Autocomplete Suggestion
    public List<String> autocomplete(String prefix) throws IOException {
        //String searchJson = "{ \"suggest\": { \"product_suggest\": { \"prefix\": \"" + prefix + "\", \"completion\": { \"field\": \"name\" } } } }";

        MatchPhrasePrefixQueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("name", prefix);

        // Build search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        SearchRequest request = new SearchRequest(INDEX_NAME);
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
