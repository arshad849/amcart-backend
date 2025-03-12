/*
package com.nagp.products.service;

@Slf4j
@Service
public class OpenSearchService {
    private final String INDEX_NAME = "products";

    public void findByLastName(final String lastName) {

        var match= QueryBuilders.match().field("lastname").query(FieldValue.of(lastName)).fuzziness("AUTO").build();

        NativeQuery searchQuery = new NativeQueryBuilder().withQuery(match).build();
        SearchHits<Customer> productHits =
                elasticsearchOperations
                        .search(searchQuery, Customer.class,
                                IndexCoordinates.of(BOOTFULSEARCH));

        log.info("productHits {} {}", productHits.getSearchHits().size(), productHits.getSearchHits());

        List<SearchHit<Customer>> searchHits =
                productHits.getSearchHits();
        int i = 0;
        for (SearchHit<Customer> searchHit : searchHits) {
            log.info("searchHit {}", searchHit);
        }

    }
}

*/
/*import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.products.model.ProductModel;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class OpenSearchService {
    @Autowired
    private RestHighLevelClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String INDEX_NAME = "products";

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

    public String saveProduct(ProductModel product) throws IOException {
        IndexRequest request = new IndexRequest(INDEX_NAME)
                .id(product.getProductId())
                .source(objectMapper.writeValueAsString(product), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.getId();
    }

    public List<ProductModel> searchProducts(String query) throws IOException {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "name", "description");

        // Build the search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        SearchRequest request = new SearchRequest(INDEX_NAME);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<ProductModel> products = new ArrayList<>();
        response.getHits().forEach(hit -> {
            try {
                products.add(objectMapper.readValue(hit.getSourceAsString(), ProductModel.class));
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                e.printStackTrace();
            }
        });

        return products;
    }

    public List<String> autocomplete(String prefix) throws IOException {

        MatchPhrasePrefixQueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("name", prefix);

        // Build search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        SearchRequest request = new SearchRequest(INDEX_NAME);
        request.source(searchSourceBuilder);

        // Execute search
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<String> suggestions = new ArrayList<>();
        response.getHits().forEach(hit -> {
            suggestions.add(hit.getSourceAsMap().get("name").toString());
        });

        return suggestions;
    }
}*//*

*/
/*import com.nagp.products.model.ProductModel;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.*;

import java.util.List;
import java.util.ArrayList;
public class OpenSearchService {

    private final OpenSearchClient client;

    public OpenSearchService(OpenSearchClient client) {
        this.client = client;
    }

//    public boolean createIndex() {
//        try {
//            CreateIndexResponse response = client.createIndex(CreateIndexRequest.builder()
//                    .index("products")
//                    .build());
//            return response.acknowledged();
//        } catch (OpenSearchException e) {
//            System.out.println("Index already exists or error: " + e.getMessage());
//            return false;
//        }
//    }

    public String saveProduct(ProductModel product) {
        try {
            IndexResponse response = client.index(IndexRequest.builder()
                    .index("products")
                    .id(product.getProductId())
                    .document(product)
                    .build());
            return response.result().toString();
        } catch (Exception e) {
            System.out.println("Error indexing document: " + e.getMessage());
            return null;
        }
    }

    public List<ProductModel> searchProducts(String query) {
        List<ProductModel> products = new ArrayList<>();
        try {
            SearchResponse<ProductModel> response = client.search(SearchRequest.builder()
                    .index("products")
                    .q(query)
                    .build(), ProductModel.class);

            response.hits().hits().forEach(hit -> products.add(hit.source()));
        } catch (Exception e) {
            System.out.println("Error searching: " + e.getMessage());
        }
        return products;
    }
}*//*


@Slf4j
@Service
public class OpenSearchService {
    private final String INDEX_NAME = "products";

    public void findByLastName(final String lastName) {

        var match= QueryBuilders.match().field("lastname").query(FieldValue.of(lastName)).fuzziness("AUTO").build();

        NativeQuery searchQuery = new NativeQueryBuilder().withQuery(match).build();
        SearchHits<Customer> productHits =
                elasticsearchOperations
                        .search(searchQuery, Customer.class,
                                IndexCoordinates.of(BOOTFULSEARCH));

        log.info("productHits {} {}", productHits.getSearchHits().size(), productHits.getSearchHits());

        List<SearchHit<Customer>> searchHits =
                productHits.getSearchHits();
        int i = 0;
        for (SearchHit<Customer> searchHit : searchHits) {
            log.info("searchHit {}", searchHit);
        }

    }
}


*/
