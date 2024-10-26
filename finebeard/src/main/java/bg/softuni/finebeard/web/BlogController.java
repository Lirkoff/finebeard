package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.AddArticleDTO;
import bg.softuni.finebeard.model.entity.BlogArticleEntity;
import bg.softuni.finebeard.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequestMapping("/blog")
@Controller
public class BlogController {


    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping("")
    public String getAllBlogArticles(Model model, Pageable pageable) {
        Page<BlogArticleEntity> blogArticleEntities = blogService.getAllArticles(pageable);

        model.addAttribute("blogArticles", blogArticleEntities);


        return "blog";
    }


    @GetMapping("/add")
    public String getAddArticlePage(Model model) {

        if (!model.containsAttribute("addArticleDTO")) {
            model.addAttribute("addArticleDTO", AddArticleDTO.empty());
        }


        return "add-article";
    }


    @PostMapping("/add")
    public String createAndRedirectBlogArticle(@Valid @ModelAttribute("addArticleDTO") AddArticleDTO addArticleDTO,
                                               BindingResult bindingResult,
                                               RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addArticleDTO", addArticleDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addArticleDTO", bindingResult);
            return "redirect:/blog/add";
        }

        BlogArticleEntity newArticle = blogService.saveArticle(addArticleDTO);


        String articleContent = readArticleContent(newArticle.getContentPath());
        rAtt.addAttribute("articleContent", articleContent);


        return "redirect:/blog/" + newArticle.getUuid();
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


        String articleContent = readArticleContent(blogArticleEntity.getContentPath());
        model.addAttribute("articleContent", articleContent);

        return "article";
    }


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


    private String readArticleContent(String contentPath) {
        Path filePath = Paths.get(contentPath);
        String articleContent = "";

        try {
            articleContent = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            articleContent = "Could not read the file. Error: " + e.getMessage();
        }
        return articleContent;
    }
}
