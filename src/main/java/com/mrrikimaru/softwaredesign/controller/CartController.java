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

//    @PostMapping("/decreaseQuantity")
//    public ResponseEntity<Cart> decreaseQuantity(@RequestParam("productId") Long productId) {
//        cartService.decreaseQuantity(productId);
//        return ResponseEntity.ok(cartService.getCart());
//    }
//
//    @PostMapping("/increaseQuantity")
//    public ResponseEntity<Cart> increaseQuantity(@RequestParam("productId") Long productId) {
//        cartService.increaseQuantity(productId);
//        return ResponseEntity.ok(cartService.getCart());
//    }
}


//import com.mrrikimaru.softwaredesign.model.Product;
//import com.mrrikimaru.softwaredesign.service.CartService;
//import com.mrrikimaru.softwaredesign.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class CartController {
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private CartService cartService;
//
//    @GetMapping("/cart")
//    public String cartPage(Model model) {
//        model.addAttribute("items", cartService.getItems());
//        model.addAttribute("cartItemCount", cartService.getItemCount());
//        return "cart";
//    }
//
////    @PostMapping("/cart/decreaseQuantity")
////    public String decreaseQuantity(@RequestParam Long productId) {
////        cartService.decreaseQuantity(productId);
////        return "redirect:/"; // Change to redirect to the current page if necessary
////    }
//
//    @PostMapping("/cart/increaseQuantity")
//    public String increaseQuantity(@RequestParam Long productId) {
//        cartService.increaseQuantity(productId);
//        return "redirect:/"; // Change to redirect to the current page if necessary
//    }
//}
