package com.mrrikimaru.softwaredesign.controller;

import com.mrrikimaru.softwaredesign.repository.ProductRepository;
import com.mrrikimaru.softwaredesign.model.Product;
import com.mrrikimaru.softwaredesign.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static String UPLOADED_FOLDER = "uploads/";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "/admin/admin";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("category") String category,
                             @RequestParam("cost") BigDecimal cost,
                             @RequestParam("imgURL") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads", fileName);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload image");
            return "redirect:/admin";
        }
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setCost(cost);
        product.setImgURL("/uploads/" + fileName);
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product added successfully");
        return "redirect:/admin";
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam("id") Long id,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("category") String category,
                              @RequestParam("cost") BigDecimal cost,
                              @RequestParam("imgURL") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Product not found");
            return "redirect:/admin";
        }

        Product product = optionalProduct.get();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setCost(cost);

        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + "_" + fileName;

            try {
                Path path = Paths.get("uploads");
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                String oldImgPath = product.getImgURL();
                if (oldImgPath != null && !oldImgPath.isEmpty()) {
                    // Удаляем старое изображение
                    Path oldPath = Paths.get("uploads", oldImgPath.substring(oldImgPath.lastIndexOf('/') + 1));
                    System.out.println("Image deleted: " + oldPath.toAbsolutePath().toString());
                    Files.deleteIfExists(oldPath);
                }

                // Устанавливаем новый путь к изображению
                product.setImgURL("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Failed to upload new image");
                return "redirect:/admin";
            }
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product updated successfully");
        return "redirect:/admin";
    }


    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Product not found");
            return "redirect:/admin";
        }
        Product product = optionalProduct.get();
        String imgPath = product.getImgURL();
        if (imgPath != null && !imgPath.isEmpty()) {
            // Get the file name from the URL
            Path path = Paths.get("uploads", imgPath.substring(imgPath.lastIndexOf('/') + 1));
            try {
                Files.deleteIfExists(path);
                System.out.println("Image deleted: " + path.toAbsolutePath().toString());
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Failed to delete image");
                return "redirect:/admin";
            }
        }
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully");
        return "redirect:/admin";
    }
}


