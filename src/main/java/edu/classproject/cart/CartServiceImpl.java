package edu.classproject.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(String customerId, String restaurantId) {
        String cartId = UUID.randomUUID().toString();
        Cart cart = new Cart(cartId, customerId, restaurantId, new ArrayList<>());
        return cartRepository.save(cart);
    }

    @Override
    public Cart addItem(String cartId, String menuItemId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartLine> updatedLines = new ArrayList<>(cart.lines());

        boolean found = false;

        for (int i = 0; i < updatedLines.size(); i++) {
            CartLine line = updatedLines.get(i);

            if (line.menuItemId().equals(menuItemId)) {
                updatedLines.set(i,
                        new CartLine(menuItemId, line.itemName(), line.quantity() + quantity));
                found = true;
                break;
            }
        }

        if (!found) {
            // name is dummy for now (can integrate with restaurant later)
            updatedLines.add(new CartLine(menuItemId, "Item-" + menuItemId, quantity));
        }

        Cart updatedCart = new Cart(
                cart.cartId(),
                cart.customerId(),
                cart.restaurantId(),
                updatedLines
        );

        return cartRepository.save(updatedCart);
    }

    @Override
    public Cart getCart(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Cart clearedCart = new Cart(
                cart.cartId(),
                cart.customerId(),
                cart.restaurantId(),
                new ArrayList<>()
        );

        cartRepository.save(clearedCart);
    }

    @Override
    public Cart updateItem(String cartId, String menuItemId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartLine> updatedLines = new ArrayList<>();

        boolean found = false;

        for (CartLine line : cart.lines()) {
            if (line.menuItemId().equals(menuItemId)) {
                updatedLines.add(new CartLine(menuItemId, line.itemName(), quantity));
                found = true;
            } else {
                updatedLines.add(line);
            }
        }

        if (!found) {
            throw new RuntimeException("Item not found in cart");
        }

        Cart updatedCart = new Cart(
                cart.cartId(),
                cart.customerId(),
                cart.restaurantId(),
                updatedLines
        );

        return cartRepository.save(updatedCart);
    }

    @Override
    public Cart removeItem(String cartId, String menuItemId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartLine> updatedLines = new ArrayList<>();

        for (CartLine line : cart.lines()) {
            if (!line.menuItemId().equals(menuItemId)) {
                updatedLines.add(line);
            }
        }

        Cart updatedCart = new Cart(
                cart.cartId(),
                cart.customerId(),
                cart.restaurantId(),
                updatedLines
        );

        return cartRepository.save(updatedCart);
    }
}