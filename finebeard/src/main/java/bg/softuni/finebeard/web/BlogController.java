package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.entity.BlogArticle;
import bg.softuni.finebeard.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {
    

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog/{id}")
    public String getBlogPost(@PathVariable("id") Long id, Model model) {
        BlogArticle blogPost = blogService.getArticleById(id);
        model.addAttribute("blogPost", blogPost);
        return "blogPost";
    }

    @GetMapping("/blog")
    public String getAllBlogPosts(Model model) {
        List<BlogArticle> blogPosts = blogService.getAllArticles();
        model.addAttribute("blogPosts", blogPosts);
        return "blogList";
    }

    @PostMapping("/blog")
    public String createBlogPost(@RequestBody BlogArticle blogArticle) {
        blogService.saveArticle(blogArticle);
        return "redirect:/blogs";
    }

    @PutMapping("/blog/{id}")
    public String updateBlogPost(@PathVariable("id") Long id, @RequestBody BlogArticle updatedBlogArticle) {
        blogService.updateBlogArticle(id, updatedBlogArticle);
        return "redirect:/blog/" + id;
    }

    @DeleteMapping("/blog/{id}")
    public String deleteBlogPost(@PathVariable("id") Long id) {
        blogService.deleteArticleById(id);
        return "redirect:/blogs";
    }
    
}
