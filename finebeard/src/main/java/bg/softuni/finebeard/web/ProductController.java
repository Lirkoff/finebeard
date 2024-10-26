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

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


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


    @DeleteMapping("/delete/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid) {

        productService.deleteProduct(uuid);


        return "redirect:/shop/categories";
    }

    @GetMapping("/update/{uuid}")
    public String update(@PathVariable("uuid") UUID uuid,
                         Model model) {

        ProductEntity product = productService.getByUuid(uuid);

        model.addAttribute("product", product);


        return "products-update";
    }

    @PatchMapping("/update/{uuid}")
    public String update(@PathVariable("uuid") UUID uuid,
                         ProductEntity product) {

        ProductEntity existingProduct = productService.getByUuid(uuid);


        if (existingProduct != null) {
            if (product.getBrand().getModel() != null) {
                existingProduct.getBrand().setModel(product.getBrand().getModel());
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
