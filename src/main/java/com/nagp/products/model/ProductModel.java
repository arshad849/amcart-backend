package com.nagp.products.model;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductModel {
    private String productId;
    private String name;
    private String description;
    private double price;
    private String category;
    private String subCategory;
    private List<String> images;
    private int stock;
    private double rating;
    private List<String> color;
    private List<String> size;
    private Map<String, String> attributes;
    private String label;
}
