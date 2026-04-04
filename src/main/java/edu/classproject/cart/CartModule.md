## Cart Management Module (Team 6)

### Overview
The Cart Management module allows users to create and manage a shopping cart for a single restaurant. It supports adding, updating, removing items, and clearing the cart.

### Features
- Create cart for a user and restaurant
- Add items to cart
- Update item quantity
- Remove items from cart
- Clear entire cart
- Quantity validation (must be > 0)

### Design Decisions
- Cart is associated with a single restaurant via restaurantId
- Single restaurant constraint is enforced structurally (cart cannot change restaurant)
- Used in-memory repository for storage
- Followed interface-based design (CartService, CartRepository)

### Edge Cases Handled
- Adding item with invalid quantity throws exception
- Updating non-existing item throws exception
- Removing item updates cart correctly

### Integration Points
- Depends on restaurant module only for restaurantId and menuItemId
- No direct dependency on restaurant implementation (loose coupling)

### Testing
- Unit tests written using JUnit 5
- Covers:
  - Happy paths (add/update/remove/clear)
  - Edge cases (invalid quantity, missing item)
