package edu.classproject.cart;

import java.util.Optional;

public interface CartRepository {

    Cart save(Cart cart);

    Optional<Cart> findById(String cartId);

    void delete(String cartId);
}