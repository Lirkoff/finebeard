package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.CategoryService;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final CategoryService categoryService;

    public ShopController(ShopService shopService, CategoryService categoryService) {
        this.shopService = shopService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String allCategories(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());

        return "shop";
    }


    @PostMapping("/categories/{categoryName}")
    public String categoryProducts(@PathVariable("categoryName") String categoryName,
                                   @RequestParam("categoryId") Long categoryId,
                                   Model model,
                                   Pageable pageable) {

        try {
            Page<ProductEntity> products = shopService.getAllProducts(categoryId, pageable);
            model.addAttribute("products", products);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "category-products";
    }

//    @PostMapping("/categories/{categoryName}")
//    public String categoryProducts(@PathVariable("categoryName") String categoryName, Model model, Pageable pageable) {
//
//
//        return "category-products";
//    }
//    @GetMapping("/categories/{categoryName}/products/{uuid}")
//    public String categoryProducts(@PathVariable("categoryName") String categoryName,
//                                   @PathVariable("uuid") UUID uuid) {
//
//        return "product-details";
//    }


}
