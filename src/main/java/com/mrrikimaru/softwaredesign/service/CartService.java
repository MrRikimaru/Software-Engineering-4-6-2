package com.mrrikimaru.softwaredesign.service;

import com.mrrikimaru.softwaredesign.model.Cart;
import com.mrrikimaru.softwaredesign.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Service
@SessionScope
public class CartService {
    private Map<Long, Cart> cartItems = new HashMap<>();

    public void decreaseQuantity(Long productId) {
        Cart item = cartItems.get(productId);
        if (item != null && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            cartItems.remove(productId);
        }
    }

    public void increaseQuantity(Long productId) {
        Cart item = cartItems.get(productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    private List<Cart> items = new ArrayList<>();

    public List<Cart> getItems() {
        return items;
    }

    public void addItem(Product product) {
        Optional<Cart> cartItem = items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(cartItem.get().getQuantity() + 1);
        } else {
            items.add(new Cart(product, 1));
        }
    }

    public int getItemCount() {
        return items.stream().mapToInt(Cart::getQuantity).sum();
    }

    public void clear() {
        items.clear();
    }

}
