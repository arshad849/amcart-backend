/*

package com.nagp.products.config;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.signer.Aws4Signer;
import software.amazon.awssdk.auth.signer.params.Aws4SignerParams;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

public class AWSRequestSigningInterceptor implements HttpRequestInterceptor {
    private final Aws4Signer signer;
    private final DefaultCredentialsProvider credentialsProvider;
    private final String serviceName;
    private final Region region;

    public AWSRequestSigningInterceptor(String serviceName, Region region) {
        this.signer = Aws4Signer.create();
        this.credentialsProvider = DefaultCredentialsProvider.create();
        this.serviceName = serviceName;
        this.region = region;
    }

    @Override
    public void process(HttpRequest request, HttpContext context) {
        Aws4SignerParams signingParams = Aws4SignerParams.builder()
                .signingName(serviceName)
                .signingRegion(region)
                .awsCredentials(credentialsProvider.resolveCredentials()) // Fetch IAM credentials
                .build();

        // Convert Apache HttpRequest to AWS SDK's SdkHttpFullRequest
        SdkHttpFullRequest sdkRequest = SdkHttpFullRequest.builder()
                .uri(URI.create(request.getRequestLine().getUri()))
                .method(SdkHttpMethod.valueOf(request.getRequestLine().getMethod()))
                .build();

        // Sign the request
        signer.sign(sdkRequest, signingParams);
    }
}

*/
