package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.CategoryService;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.ShopService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public ShopController(ShopService shopService, CategoryService categoryService, ProductService productService) {
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public String allCategories(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());

        return "shop";
    }

    @PostMapping("/categories")
    public String allCategories(@RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryId") Long categoryId,
                                RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("categoryId", categoryId);

        return "redirect:/shop/categories/" + categoryName + "/products";
    }


    @GetMapping("/categories/{categoryName}/products")
    public String allProductsInCategory(@PathVariable("categoryName") String categoryName,
                                        @ModelAttribute("categoryId") Long categoryId,
                                        Model model,
                                        Pageable pageable) {

        try {
            Page<ProductEntity> products = shopService.getAllProducts(categoryId, pageable);
            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "category-products";
    }


    @PostMapping("/categories/{categoryName}/products")
    public String categoryProducts(@PathVariable("categoryName") String categoryName,
                                   @RequestParam("uuid") UUID uuid,
                                   Model model) {


        model.addAttribute("uuid", uuid);

        return "redirect:/shop/categories/" + categoryName + "/products/" + uuid;
    }


    @GetMapping("/categories/{categoryName}/products/{uuid}")
    public String categoryProduct(@PathVariable("categoryName") String categoryName,
                                  @ModelAttribute("uuid") UUID uuid,
                                  Model model) {

        ProductDetailDTO productDetailDTO = productService
                .getProductDetail(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Product with uuid " + uuid + " not found!"));

        model.addAttribute("productDetailDTO", productDetailDTO);

        return "product-details";
    }


}
