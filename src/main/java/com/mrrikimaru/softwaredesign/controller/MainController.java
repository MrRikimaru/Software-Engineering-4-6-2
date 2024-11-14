package com.mrrikimaru.softwaredesign.controller;

import com.mrrikimaru.softwaredesign.model.Product;
import com.mrrikimaru.softwaredesign.service.CartService;
import com.mrrikimaru.softwaredesign.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "index";
    }

    @GetMapping("/catalog/apiece")
    public String getProductsApiece(Model model) {
        model.addAttribute("cartItemCount", cartService.getItemCount());
        model.addAttribute("cartServiceItems", cartService.getItems());
        model.addAttribute("products", productService.getProductsByCategoryApiece());
        return "/catalog/apiece";
    }
    @PostMapping("/catalog/apiece/addToCart")
    public String addToCart(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(productId).orElse(null);
        if (product != null) {
            cartService.addItem(product);
        }
        redirectAttributes.addFlashAttribute("message", "Product added to cart");
        return "redirect:/catalog/apiece";
    }
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Логика добавления товара в корзину
        cartService.decreaseQuantity(productId);
        // Перенаправляем пользователя обратно на текущую страницу
        return "redirect:" + request.getRequestURI();
    }

    @GetMapping("/cart/decreaseQuantity")
    public String decreaseQuantity(@RequestParam Long productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        cartService.decreaseQuantity(productId);
        return "redirect:" + request.getRequestURI();
    }

    @GetMapping("/catalog/composition")
    public String composition(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryComposition());
        return "/catalog/composition";
    }

    @GetMapping("/catalog/bouquets")
    public String bouquets(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryBouquets());
        return "/catalog/bouquets";
    }

    @GetMapping("/catalog/roses")
    public String roses(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryRoses());
        return "/catalog/roses";
    }

    @GetMapping("/catalog/toys")
    public String toys(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryToys());
        return "/catalog/toys";
    }

    @GetMapping("/catalog/balloons")
    public String balloons(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryBalloons());
        return "/catalog/balloons";
    }

    @GetMapping("/catalog/present")
    public String present(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryPresent());
        return "/catalog/present";
    }

    @GetMapping("/catalog/postcards")
    public String postcards(Model model) {
        model.addAttribute("products", productService.getProductsByCategoryPostcards());
        return "/catalog/postcards";
    }
}
