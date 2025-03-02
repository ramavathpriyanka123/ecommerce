
package com.excelr.service;

import com.excelr.model.*;
import com.excelr.repo.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ElectronicsRepository electronicsRepository;

    @Autowired
    private FootwearRepository footwearRepository;

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private CosmeticsRepository cosmeticsRepository;

    @Autowired
    private LaptopsRepository laptopsRepository;

    @Autowired
    private MensRepository menRepository;

    @Autowired
    private WomenRepository womenRepository;

    @Autowired
    private MobilesRepository mobilesRepository;

    @Autowired
    private ToysRepository toysRepository;

    @Autowired
    private KidsRepository kidsRepository;

    @Autowired
    private UserRepository userRepository; 
    public Cart addItemToCart(Long userId, Long productId, Integer qty, String category) {
       
        Cart cart = getOrCreateCart(userId);

        Optional<CartItem> existingCartItem = cart.getItems().stream()
            .filter(cartItem -> isSameProduct(cartItem, productId, category))
            .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQty(cartItem.getQty() + qty);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setQty(qty);
            cartItem.setPrice(calculatePrice(category, productId));

            switch (category) {
                case "electronics":
                    cartItem.setElectronics(electronicsRepository.findById(productId).orElse(null));
                    break;
                case "footwear":
                    cartItem.setFootwear(footwearRepository.findById(productId).orElse(null));
                    break;
                case "grocery":
                    cartItem.setGrocery(groceryRepository.findById(productId).orElse(null));
                    break;
                case "cosmetics":
                    cartItem.setCosmetics(cosmeticsRepository.findById(productId).orElse(null));
                    break;
                case "laptops":
                    cartItem.setLaptops(laptopsRepository.findById(productId).orElse(null));
                    break;
                case "men":
                    cartItem.setMenClothing(menRepository.findById(productId).orElse(null));
                    break;
                case "women":
                    cartItem.setWomenClothing(womenRepository.findById(productId).orElse(null));
                    break;
                case "mobiles":
                    cartItem.setMobiles(mobilesRepository.findById(productId).orElse(null));
                    break;
                case "toys":
                    cartItem.setToys(toysRepository.findById(productId).orElse(null));
                    break;
                case "kids":
                    cartItem.setKidsClothing(kidsRepository.findById(productId).orElse(null));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid category: " + category);
            }

            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);

        return cart;
    }

    private boolean isSameProduct(CartItem cartItem, Long productId, String category) {
        switch (category) {
            case "electronics":
                return cartItem.getElectronics() != null && cartItem.getElectronics().getId().equals(productId);
            case "footwear":
                return cartItem.getFootwear() != null && cartItem.getFootwear().getId().equals(productId);
            case "grocery":
                return cartItem.getGrocery() != null && cartItem.getGrocery().getId().equals(productId);
            case "cosmetics":
                return cartItem.getCosmetics() != null && cartItem.getCosmetics().getId().equals(productId);
            case "laptops":
                return cartItem.getLaptops() != null && cartItem.getLaptops().getId().equals(productId);
            case "men":
                return cartItem.getMenClothing() != null && cartItem.getMenClothing().getId().equals(productId);
            case "women":
                return cartItem.getWomenClothing() != null && cartItem.getWomenClothing().getId().equals(productId);
            case "mobiles":
                return cartItem.getMobiles() != null && cartItem.getMobiles().getId().equals(productId);
            case "toys":
                return cartItem.getToys() != null && cartItem.getToys().getId().equals(productId);
            case "kids":
                return cartItem.getKidsClothing() != null && cartItem.getKidsClothing().getId().equals(productId);
            default:
                return false;
        }
    }

    private Cart getOrCreateCart(Long userId) {
        com.excelr.model.User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);  
        }

        return cart;
    }

    @Transactional
    public void clearCart(Long userId) {
  
        cartItemRepository.deleteByCartUserId(userId);
        cartRepository.deleteByUserId(userId);
    }
    
    private Double calculatePrice(String category, Long productId) {
        switch (category) {
            case "electronics":
                return electronicsRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                           .getPrice();
            case "footwear":
                return footwearRepository.findById(productId)
                                         .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                         .getPrice();
            case "grocery":
                return groceryRepository.findById(productId)
                                        .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                        .getPrice();
            case "cosmetics":
                return cosmeticsRepository.findById(productId)
                                          .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                          .getPrice();
            case "laptops":
                return laptopsRepository.findById(productId)
                                        .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                        .getPrice();
            case "men":
                return menRepository.findById(productId)
                                    .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                    .getPrice();
            case "women":
                return womenRepository.findById(productId)
                                      .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                      .getPrice();
            case "mobiles":
                return mobilesRepository.findById(productId)
                                        .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                        .getPrice();
            case "toys":
                return toysRepository.findById(productId)
                                     .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                     .getPrice();
            case "kids":
                return kidsRepository.findById(productId)
                                     .orElseThrow(() -> new IllegalArgumentException("Product not found"))
                                     .getPrice();
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    public Cart deleteItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(cartItem -> cartItem.getId().equals(itemId));
        cartRepository.save(cart);
        return cart;
    }
    
    public Cart updateItemQuantity(Long userId, Long cartItemId, Integer qty) {
        if (qty < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user with ID: " + userId);
        }

        Optional<CartItem> optionalCartItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQty(qty); 
            cartRepository.save(cart);  
        } else {
            throw new IllegalArgumentException("Item not found with ID: " + cartItemId);
        }

        return cart;  
    }

    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

