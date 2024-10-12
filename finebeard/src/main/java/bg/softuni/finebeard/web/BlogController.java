package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.entity.BlogArticleEntity;
import bg.softuni.finebeard.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/blog")
public class BlogController {


    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping("")
    public String getAllBlogArticles(Model model) {
        List<BlogArticleEntity> blogArticleEntities = blogService.getAllArticles();
        model.addAttribute("blogArticles", blogArticleEntities);


        return "blog";
    }
    
    
    @GetMapping("/add")
    public String getAddArticlePage(Model model) {
        model.addAttribute("blogArticle", new BlogArticleEntity());


        return "add-article";
    }


    @PostMapping("/add")
    public String createAndRedirectBlogArticle(@ModelAttribute BlogArticleEntity blogArticleEntity,
                                               BindingResult bindingResult,
                                               RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("blogArticleEntity", blogArticleEntity);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.blogArticleEntity", bindingResult);
            return "redirect:/blog/add";
        }

        UUID newArticle = blogService.saveArticle(blogArticleEntity);

        Path filePath = Paths.get(blogArticleEntity.getContent());

        String articleContent = "";

        try {
            articleContent = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            articleContent = "Could not read the file. Error: " + e.getMessage();
        }

        rAtt.addAttribute("articleContent", articleContent);

        return "redirect:/blog/" + newArticle;
    }


    @GetMapping("/{uuid}")
    public String getBlogPost(@PathVariable("uuid") UUID uuid,
                              @ModelAttribute("articleContent") String articleContent,
                              Model model) {

        BlogArticleEntity blogArticleEntity = blogService.getArticleByUuid(uuid);
        model.addAttribute("blogArticle", blogArticleEntity);


        return "article";
    }

    @PostMapping("/{uuid}")
    public String getBlogPost(@PathVariable("uuid") UUID uuid,
                              Model model) {

        BlogArticleEntity blogArticleEntity = blogService.getArticleByUuid(uuid);
        model.addAttribute("blogArticle", blogArticleEntity);

        Path filePath = Paths.get(blogArticleEntity.getContent());

        String articleContent = "";

        try {
            articleContent = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            articleContent = "Could not read the file. Error: " + e.getMessage();
        }

        model.addAttribute("articleContent", articleContent);

        return "article";
    }

//    @PostMapping("/{uuid}")
//    public String createBlogArticle(@RequestBody BlogArticleEntity blogArticleEntity) {
//        blogService.saveArticle(blogArticleEntity);
//        return "redirect:/blog";
//    }

    @PutMapping("/{uuid}")
    public String updateBlogPost(@PathVariable("uuid") Long id, @RequestBody BlogArticleEntity updatedBlogArticleEntity) {
        blogService.updateBlogArticle(id, updatedBlogArticleEntity);
        return "redirect:/blog/" + id;
    }


    @DeleteMapping("/{id}")
    public String deleteBlogPost(@PathVariable("id") Long id) {
        blogService.deleteArticleById(id);
        return "redirect:/blog";
    }

}
