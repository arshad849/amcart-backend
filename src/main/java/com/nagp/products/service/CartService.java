package com.nagp.products.service;

import com.nagp.products.dao.CartRepository;
import com.nagp.products.entity.Cart;
import com.nagp.products.entity.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            return cartRepository.save(cart);
        });
    }

    public Cart addItem(String userId, String productId, int quantity, double price) {
        Cart cart = getCart(userId);
        CartItem item = new CartItem(null, cart, productId, quantity, price);
        cart.getCartItems().add(item);
        return cartRepository.save(cart);
    }

    public void clearCart(String userId) {
        Cart cart = getCart(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public void removeItem(String userId, String productId) {
        Cart cart = getCart(userId);
        cart.getCartItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }
}

