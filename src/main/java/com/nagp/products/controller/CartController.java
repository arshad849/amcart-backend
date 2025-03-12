package com.nagp.products.controller;

import com.nagp.products.entity.Cart;
import com.nagp.products.model.CartItemRequest;
import com.nagp.products.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping
    public ResponseEntity<Cart> addItem(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(
                cartService.addItem(request.getUserId(), request.getProductId(), request.getQuantity(), request.getPrice())
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@RequestParam String userId, @PathVariable String productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
}

