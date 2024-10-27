package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.service.CategoryService;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.ShopService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * ShopController handles all the HTTP requests related to the shop, categories, and products.
 * It interacts with ShopService, CategoryService, and ProductService to manage the overall shop functionalities.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    /**
     * Manages the shop-related operations such as retrieving product information.
     * This service is essential for handling business logic associated with shops,
     * and it is used within the ShopController to interact with the shop's product data.
     * The shopService is immutable and assigned during the construction of the ShopController.
     */
    private final ShopService shopService;

    /**
     * The service responsible for handling category-related business logic.
     */
    private final CategoryService categoryService;

    /**
     * Provides services for managing products within the shop, including adding, retrieving,
     * deleting, and saving product details. Utilized within the ShopController to handle
     * various product-related operations like viewing product details or listing products by categories.
     */
    private final ProductService productService;

    /**
     * Constructs a new ShopController with the specified services.
     *
     * @param shopService the shop service to manage products and other shop-related operations
     * @param categoryService the category service to manage product categories
     * @param productService the product service to handle individual product operations
     */
    public ShopController(ShopService shopService, CategoryService categoryService, ProductService productService) {
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    /**
     * Handles the HTTP GET request for retrieving all categories and adds them to the model.
     *
     * @param model The model to which the categories will be added.
     * @return The view name "shop" where the categories will be displayed.
     */
    @GetMapping("/categories")
    public String allCategories(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());

        return "shop";
    }

    /**
     * Handles the form submission for displaying all categories and redirects to the products
     * page of the specific category.
     *
     * @param categoryName the name of the category to display
     * @param categoryId the ID of the category to display
     * @param redirectAttributes the redirect attributes used to pass parameters in the redirect
     * @return the redirect URL to the products page of the specific category
     */
    @PostMapping("/categories")
    public String allCategories(@RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryId") Long categoryId,
                                RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("categoryId", categoryId);

        return "redirect:/shop/categories/" + categoryName + "/products";
    }


    /**
     * Fetches all products in a specified category and adds them to the model.
     *
     * @param categoryName the name of the category to fetch products for.
     * @param categoryId the id of the category used to fetch products.
     * @param pageable the pagination information.
     * @param model the model to add attributes to.
     * @return the view name for displaying the category's products.
     */
    @GetMapping("/categories/{categoryName}/products")
    public String allProductsInCategory(@PathVariable("categoryName") String categoryName,
                                        @ModelAttribute("categoryId") Long categoryId,
                                        Pageable pageable,
                                        Model model) {

        try {
            Page<ProductEntity> products = shopService.getAllProducts(categoryId, pageable);
            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);
            model.addAttribute("categoryId", categoryId);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "category-products";
    }


    /**
     * Handles the redirection to the appropriate product page within a specific category.
     *
     * @param categoryName the name of the category to which the products belong
     * @param uuid the unique identifier for the product
     * @param model the model object to hold attributes for the view
     * @return a redirection string to the product page within the specified category
     */
    @PostMapping("/categories/{categoryName}/products")
    public String categoryProducts(@PathVariable("categoryName") String categoryName,
                                   @RequestParam("uuid") UUID uuid,
                                   Model model) {


        model.addAttribute("uuid", uuid);

        return "redirect:/shop/categories/" + categoryName + "/products/" + uuid;
    }


    /**
     * Handles GET requests for displaying product details in a specific category.
     *
     * @param uuid the unique identifier of the product
     * @param model the Model object to pass data to the view
     * @return the name of the view template for displaying product details
     */
    @GetMapping("/categories/{categoryName}/products/{uuid}")
    public String categoryProduct(@PathVariable("uuid") UUID uuid,
                                  Model model) {

        ProductDetailDTO productDetailDTO = productService
                .getProductDetail(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Product with uuid " + uuid + " not found!"));

        model.addAttribute("productDetailDTO", productDetailDTO);


        return "product-details";
    }


}
