package com.example.demo.controllers;

import com.example.demo.services.CheckoutService;
import com.example.demo.dto.Purchase;
import com.example.demo.dto.PurchaseResponse;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse checkoutCart(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.checkoutCart(purchase);

        return purchaseResponse;
    }

}