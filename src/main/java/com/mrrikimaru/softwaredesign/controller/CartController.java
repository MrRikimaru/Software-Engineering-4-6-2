package com.mrrikimaru.softwaredesign.controller;

import com.mrrikimaru.softwaredesign.model.Cart;
import com.mrrikimaru.softwaredesign.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String cartPage(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "cart";
    }
}
