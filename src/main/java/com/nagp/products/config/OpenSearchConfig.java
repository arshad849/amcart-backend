
package com.nagp.products.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Configuration
public class OpenSearchConfig {

    @Value("${aws.opensearch.endpoint}")
    private String OPENSEARCH_DOMAIN;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public RestClient restClient() {
        return RestClient.builder( new HttpHost(OPENSEARCH_DOMAIN, 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    return httpClientBuilder.addInterceptorFirst(
                            new AWSRequestSigningInterceptor("es", Region.of(awsRegion))
                    );
                })
                .build();
    }
}
/*
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.opensearch.client.RestClient;
//import org.opensearch.client.RestClientBuilder;
//import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.signer.Aws4Signer;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;

import java.net.URI;

@Configuration
public class OpenSearchConfig {

    @Value("${aws.opensearch.endpoint}")
    private String dynamodbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    public OpenSearchClient openSearchClient() {
        return OpenSearchClient.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create()) // Uses IAM role
                .httpClient(ApacheHttpClient.create())
                .endpointOverride(URI.create(dynamodbEndpoint)) // OpenSearch endpoint
                .build();
    }

    */
/*public RestHighLevelClient openSearchClient() {
        int port = 443;
        String scheme = "https";
        Region region = Region.AP_SOUTH_1; // Set your AWS region

        AWSRequestSigningInterceptor interceptor = new AWSRequestSigningInterceptor("es", region);

        RestClientBuilder builder = RestClient.builder(new HttpHost(dynamodbEndpoint, port, scheme))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.addInterceptorFirst(interceptor)
                );

        return new RestHighLevelClient(builder);
    }*//*


   */
/* @Bean
    public RestHighLevelClient openSearchClient() {
        String hostname = "search-amcart-product-zkmyrv5exrdc4a7ks3h5bm6goi.ap-south-1.es.amazonaws.com";
        int port = 443; // Use 443 for HTTPS
        String scheme = "https";
        String username = "arshad849";
        String password = "Arshad@849";

        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(hostname, port, scheme))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }*//*



}
*/
