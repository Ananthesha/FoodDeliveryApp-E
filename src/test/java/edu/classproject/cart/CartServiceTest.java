package edu.classproject.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    void setup() {
        cartService = new CartServiceImpl(new InMemoryCartRepository());
    }

    @Test
    void testCreateAndAddItem() {
        Cart cart = cartService.createCart("user1", "rest1");

        cart = cartService.addItem(cart.cartId(), "item1", 2);

        assertEquals(1, cart.lines().size());
        assertEquals(2, cart.lines().get(0).quantity());
    }

    @Test
    void testUpdateItem() {
        Cart cart = cartService.createCart("user1", "rest1");
        cart = cartService.addItem(cart.cartId(), "item1", 2);

        cart = cartService.updateItem(cart.cartId(), "item1", 5);

        assertEquals(5, cart.lines().get(0).quantity());
    }

    @Test
    void testRemoveItem() {
        Cart cart = cartService.createCart("user1", "rest1");
        cart = cartService.addItem(cart.cartId(), "item1", 2);

        cart = cartService.removeItem(cart.cartId(), "item1");

        assertTrue(cart.lines().isEmpty());
    }

    @Test
    void testClearCart() {
        Cart cart = cartService.createCart("user1", "rest1");
        cart = cartService.addItem(cart.cartId(), "item1", 2);

        cartService.clearCart(cart.cartId());

        Cart cleared = cartService.getCart(cart.cartId());

        assertTrue(cleared.lines().isEmpty());
    }

    // ❌ EDGE CASE 1
    @Test
    void testInvalidQuantity() {
        Cart cart = cartService.createCart("user1", "rest1");

        assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItem(cart.cartId(), "item1", 0);
        });
    }

    // ❌ EDGE CASE 2
    @Test
    void testUpdateNonExistingItem() {
        Cart cart = cartService.createCart("user1", "rest1");

        assertThrows(RuntimeException.class, () -> {
            cartService.updateItem(cart.cartId(), "item1", 5);
        });
    }
}