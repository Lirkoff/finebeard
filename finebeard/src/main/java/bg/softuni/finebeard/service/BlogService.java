package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.BlogArticle;

import java.util.List;

public interface BlogService {

    List<BlogArticle> getAllArticles();

    BlogArticle getArticleById(Long id);

    BlogArticle saveArticle(BlogArticle article);

    void deleteArticleById(Long id);

    void updateBlogArticle(Long id, BlogArticle updatedBlogArticle);
}
