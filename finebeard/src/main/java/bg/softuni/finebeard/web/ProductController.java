package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.BrandEnum;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


/**
 * Controller handling CRUD operations for products.
 */
@RequestMapping("/products")
@Controller
public class ProductController {
    /**
     * Service layer interface for managing products.
     *
     * This variable represents the dependency of ProductController on ProductService.
     * It is responsible for handling business logic related to products, such as adding,
     * deleting, fetching, and updating product details.
     *
     * Typical operations invoked through this service include:
     * - Adding a new product to the system.
     * - Retrieving detailed information about a specific product by its UUID.
     * - Deleting a product from the system.
     * - Updating an existing product's details.
     * - Saving the state of a product.
     */
    private final ProductService productService;


    /**
     * Constructs a ProductController with the given ProductService.
     *
     * @param productService the ProductService to be used by this controller
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Handles the HTTP GET request to display the add product form. Adds necessary attributes
     * for the product creation form to the model, such as an empty AddProductDTO and sorted
     * lists of available brands and categories.
     *
     * @param model the model to which attributes are added for rendering the view
     * @return the view name for adding a new product
     */
    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("addProductDTO")) {
            model.addAttribute("addProductDTO", AddProductDTO.empty());
        }

        List<BrandEnum> brandEnums = Arrays.asList(BrandEnum.values());
        brandEnums.sort(Comparator.comparing(BrandEnum::getDisplayName));

        List<ProductCategoryEnum> categoryEnums = Arrays.asList(ProductCategoryEnum.values());
        categoryEnums.sort(Comparator.comparing(ProductCategoryEnum::getDisplayName));

        model.addAttribute("brands", brandEnums);
        model.addAttribute("categories", categoryEnums);

        return "products-add";
    }

    /**
     * Handles the addition of a new product.
     *
     * @param addProductDTO Data Transfer Object containing the details of the product to be added.
     * @param bindingResult Result object holding the validation results for `addProductDTO`.
     * @param rAtt RedirectAttributes used to pass attributes to the redirect URL.
     * @return The URL to redirect to after the product is successfully added, or back to the add product page if errors occur.
     */
    @PostMapping("/add")
    public String add(
            @Valid AddProductDTO addProductDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addProductDTO", addProductDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addProductDTO", bindingResult);
            return "redirect:/products/add";
        }


        UUID newProductUUID = productService.addProduct(addProductDTO);


        String category = addProductDTO.category().getDisplayName();

        String redirectUrl = "redirect:/shop/categories/" + category + "/products/" + newProductUUID;

        return redirectUrl;
    }


    /**
     * Deletes a product with the specified UUID and redirects to the shop categories page.
     *
     * @param uuid the UUID of the product to be deleted.
     * @return a redirect instruction to the shop categories page.
     */
    @DeleteMapping("/delete/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid) {

        productService.deleteProduct(uuid);


        return "redirect:/shop/categories";
    }

    /**
     * Handles GET requests to update a product.
     *
     * @param uuid the unique identifier of the product to update
     * @param model the Spring Model object to add attributes to
     * @return the view name for updating the product
     */
    @GetMapping("/update/{uuid}")
    public String update(@PathVariable("uuid") UUID uuid,
                         Model model) {

        ProductEntity product = productService.getByUuid(uuid);


        model.addAttribute("product", product);




        return "products-update";
    }

    /**
     * Handles HTTP PATCH requests to update an existing product identified by its UUID.
     * If the product with the specified UUID exists, this method updates its details
     * with any non-null values provided in the {@code product} parameter.
     *
     * @param uuid The UUID of the product to be updated.
     * @param product The product entity containing updated values.
     * @return A redirection URL to the updated product's details page, or a redirection URL back to the update form if the product's category is null.
     */
    @PatchMapping("/update/{uuid}")
    public String update(@PathVariable("uuid") UUID uuid,
                         ProductEntity product) {



        ProductEntity existingProduct = productService.getByUuid(uuid);


        if (existingProduct != null) {
            if (product.getModel() != null) {
                existingProduct.setModel(product.getModel());
            }
            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }
            if (product.getImageUrl() != null) {
                existingProduct.setImageUrl(product.getImageUrl());
            }
            if (product.getDescription() != null) {
                existingProduct.setDescription(product.getDescription());
            }
            productService.save(existingProduct);
        }


        try {
            String categoryName = existingProduct.getCategory().getName().getDisplayName();
            return "redirect:/shop/categories/" + categoryName + "/products/" + uuid;
        } catch (NullPointerException e) {
            return "redirect:/products/update/" + uuid;
        }



    }


}
