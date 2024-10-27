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

/**
 * Controller for managing blog articles.
 * Handles creating, updating, deleting, and displaying blog articles.
 */
@RequestMapping("/blog")
@Controller
public class BlogController {


    /**
     * Service responsible for managing blog articles.
     * Handles operations such as fetching, saving, updating, and deleting blog articles.
     */
    private final BlogService blogService;

    /**
     * Constructs a BlogController with the given BlogService.
     *
     * @param blogService the blog service used to handle blog-related operations
     */
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    /**
     * Retrieves all blog articles and adds them to the model.
     *
     * @param model the model to add the blog articles to
     * @param pageable the pagination information
     * @return the view name for displaying the blog articles
     */
    @GetMapping("")
    public String getAllBlogArticles(Model model, Pageable pageable) {
        Page<BlogArticleEntity> blogArticleEntities = blogService.getAllArticles(pageable);

        model.addAttribute("blogArticles", blogArticleEntities);


        return "blog";
    }


    /**
     * Handles the GET request for displaying the "Add Article" page.
     *
     * @param model The model object used to pass attributes to the view.
     * @return The name of the view template for adding a new article.
     */
    @GetMapping("/add")
    public String getAddArticlePage(Model model) {

        if (!model.containsAttribute("addArticleDTO")) {
            model.addAttribute("addArticleDTO", AddArticleDTO.empty());
        }


        return "add-article";
    }


    /**
     * Creates a new blog article and redirects to the article's page upon successful creation.
     * If there are validation errors, redirects back to the add article page with error details.
     *
     * @param addArticleDTO Data Transfer Object containing the details of the article to be added.
     * @param bindingResult Holds the result of the validation and binding and contains errors if any.
     * @param rAtt Redirect attributes to store attributes for a redirect scenario.
     * @return The URL to redirect to, either back to the add article page on validation error or
     *         to the newly created article's page on successful creation.
     */
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


    /**
     * Handles HTTP GET requests for retrieving a blog post based on its unique UUID.
     *
     * @param uuid The unique identifier for the blog post to be retrieved.
     * @param articleContent The content of the article to be added to the model.
     * @param model The Model object to add attributes to be used for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/{uuid}")
    public String getBlogPost(@PathVariable("uuid") UUID uuid,
                              @ModelAttribute("articleContent") String articleContent,
                              Model model) {

        BlogArticleEntity blogArticleEntity = blogService.getArticleByUuid(uuid);
        model.addAttribute("blogArticle", blogArticleEntity);

        return "article";
    }

    /**
     * Retrieves a blog post by its UUID, adds the blog article and its content to the model, and returns the view name.
     *
     * @param uuid the UUID of the blog post to retrieve
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
    @PostMapping("/{uuid}")
    public String getBlogPost(@PathVariable("uuid") UUID uuid,
                              Model model) {

        BlogArticleEntity blogArticleEntity = blogService.getArticleByUuid(uuid);
        model.addAttribute("blogArticle", blogArticleEntity);


        String articleContent = readArticleContent(blogArticleEntity.getContentPath());
        model.addAttribute("articleContent", articleContent);

        return "article";
    }


    /**
     * Updates an existing blog post with the provided data.
     *
     * @param id the unique identifier of the blog post to be updated
     * @param updatedBlogArticleEntity the updated blog post data
     * @return a redirect URL to the updated blog post
     */
    @PutMapping("/{uuid}")
    public String updateBlogPost(@PathVariable("uuid") Long id, @RequestBody BlogArticleEntity updatedBlogArticleEntity) {
        blogService.updateBlogArticle(id, updatedBlogArticleEntity);
        return "redirect:/blog/" + id;
    }


    /**
     * Deletes a blog post with the given ID.
     *
     * @param id the ID of the blog post to delete
     * @return a redirection string to the blog page
     */
    @DeleteMapping("/{id}")
    public String deleteBlogPost(@PathVariable("id") Long id) {
        blogService.deleteArticleById(id);
        return "redirect:/blog";
    }


    /**
     * Reads the content of an article from the specified path.
     *
     * @param contentPath the path to the article content file
     * @return the content of the article as a string, or an error message if the file cannot be read
     */
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
