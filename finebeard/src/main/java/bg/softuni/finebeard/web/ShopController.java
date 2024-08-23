package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/categories")
    public String allCategories(Model model) {
//        model.addAttribute("products", shop.getProducts());
        return "shop";
    }

    @GetMapping("/categories/{categoryName}/products")
    public String categoryProducts(@PathVariable("categoryName") String categoryName) {

        return "category-products";
    }

//    @GetMapping("/categories/{categoryName}/products/{uuid}")
//    public String categoryProducts(@PathVariable("categoryName") String categoryName,
//                                   @PathVariable("uuid") UUID uuid) {
//
//        return "product-details";
//    }





}
