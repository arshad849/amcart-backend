package com.nagp.products.model;

import lombok.Data;

@Data
public class TokenRequest {

    private String idToken;
    private String accessToken;
    private String refreshToken;

}
