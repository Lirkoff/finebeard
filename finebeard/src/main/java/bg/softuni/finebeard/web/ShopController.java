package bg.softuni.finebeard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {



    @GetMapping("/categories")
    public String allCategories(Model model) {
//        model.addAttribute("products", shop.getProducts());
        return "shop";
    }

    @GetMapping("/categories/{categoryId}/products")
    public String categoryProducts(@PathVariable("categoryId") String uuid) {

        return "category-products";
    }

    @GetMapping("/categories/{categoryId}/products/{productId}")
    public String categoryProducts(@PathVariable("categoryId") String uuidCategory,
                                   @PathVariable("productId") String uuidProduct) {

        return "product-details";
    }



    @GetMapping("/add")
    public String add() {
        return "product-add";
    }


}
