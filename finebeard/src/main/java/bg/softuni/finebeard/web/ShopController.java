package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final CategoryRepository categoryRepository;


    public ShopController(ShopService shopService, CategoryRepository categoryRepository) {
        this.shopService = shopService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public String allCategories(Model model) {
        model.addAttribute("categories", ProductCategoryEnum.values());

        return "shop";
    }



    @GetMapping("/categories/{categoryName}")
    public String categoryProducts(@PathVariable("categoryName") String categoryName, Model model, Pageable pageable) {
        try {
            CategoryEntity category = categoryRepository.findByName(ProductCategoryEnum.valueOf(categoryName)).get();
            Page<ProductEntity> products = shopService.getAllProducts(category.getId(), pageable);
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
