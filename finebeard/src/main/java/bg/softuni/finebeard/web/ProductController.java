package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.service.BrandService;
import bg.softuni.finebeard.service.ProductService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final BrandService brandService;
    private final ProductService productService;

    public ProductController(BrandService brandService, ProductService productService) {
        this.brandService = brandService;
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("addProductDTO")) {
            model.addAttribute("addProductDTO", AddProductDTO.empty());
        }

        model.addAttribute("brands", brandService.getAllBrands());

        return "product-add";
    }

//    @PostMapping("/add")
//    public String add(
//            @Valid AddProductDTO addProductDTO,
//            BindingResult bindingResult,
//            RedirectAttributes rAtt) {
//
//        if (bindingResult.hasErrors()) {
//            rAtt.addFlashAttribute("addProductDTO", addProductDTO);
//            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addProductDTO", bindingResult);
//            return "redirect:/product/add";
//        }
//
//
//        UUID newProductUUID = productService.addProduct(addProductDTO);
//
//        return "redirect:/product/" + newProductUUID;
//    }



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
