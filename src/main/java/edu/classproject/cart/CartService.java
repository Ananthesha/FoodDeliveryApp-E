package edu.classproject.cart;

public interface CartService {
    Cart createCart(String customerId, String restaurantId);

    Cart addItem(String cartId, String menuItemId, int quantity);

    Cart getCart(String cartId);

    void clearCart(String cartId);

    Cart updateItem(String cartId, String menuItemId, int quantity);

    Cart removeItem(String cartId, String menuItemId);
}
