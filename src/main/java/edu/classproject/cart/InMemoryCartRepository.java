package edu.classproject.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCartRepository implements CartRepository {

    private final Map<String, Cart> storage = new HashMap<>();

    @Override
    public Cart save(Cart cart) {
        storage.put(cart.cartId(), cart);
        return cart;
    }

    @Override
    public Optional<Cart> findById(String cartId) {
        return Optional.ofNullable(storage.get(cartId));
    }

    @Override
    public void delete(String cartId) {
        storage.remove(cartId);
    }
}