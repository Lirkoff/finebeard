package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.enums.BrandEnum;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.service.BrandService;
import bg.softuni.finebeard.service.CategoryService;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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


//    @GetMapping("/{uuid}")
//    public String details(@PathVariable("uuid") UUID uuid, Model model) {
//        ProductDetailDTO productDetailDTO = productService
//                .getProductDetail(uuid)
//                .orElseThrow(() -> new ObjectNotFoundException("Product with uuid " + uuid + " not found!"));
//
//        model.addAttribute("productDetailDTO", productDetailDTO);
//
//        return "product-details";
//    }

//    @DeleteMapping("/{uuid}")
//    public String  delete(@PathVariable("uuid") UUID uuid) {
//
//        productService.deleteProduct(uuid);
//
//
//        return "redirect:/products/all";
//    }


}
