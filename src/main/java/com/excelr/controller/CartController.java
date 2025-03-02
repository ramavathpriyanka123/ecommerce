

package com.excelr.controller;

import com.excelr.model.Cart;
import com.excelr.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userId}/{category}/{productId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @PathVariable String category,
                                              @PathVariable Long productId, @RequestParam Integer qty) {
        try {
            if (qty < 1) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            Cart cart = cartService.addItemToCart(userId, productId, qty, category);
            return ResponseEntity.ok(cart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{userId}/{itemId}")
    public ResponseEntity<Cart> deleteItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            Cart cart = cartService.deleteItemFromCart(userId, itemId);
            return ResponseEntity.ok(cart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCart(userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{userId}/{cartItemId}")
    public ResponseEntity<Cart> updateCartItemQuantity(@PathVariable Long userId,
                                                       @PathVariable Long cartItemId,
                                                       @RequestParam Integer qty) {
        try {
            if (qty < 1) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            Cart updatedCart = cartService.updateItemQuantity(userId, cartItemId, qty);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        try {
            // Clear the cart for the given user ID
            cartService.clearCart(userId);

            return new ResponseEntity<>("Cart cleared successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to clear the cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


