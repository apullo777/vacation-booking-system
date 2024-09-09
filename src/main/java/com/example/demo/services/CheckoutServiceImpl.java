package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.dto.Purchase;
import com.example.demo.dto.PurchaseResponse;
import com.example.demo.entities.Cart;
import com.example.demo.entities.Customer;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CartRepository cartRepository;


    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse checkoutCart(Purchase purchase) {
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        if (cartItems.isEmpty()) {
            return new PurchaseResponse("Cart is empty");
        }

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);

        // populate cart with cartItems
        System.out.println("Number of items received for checkout: " + cartItems.size());
        cartItems.forEach(item -> cart.add(item));

        // save to the database
        cartRepository.save(cart);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
