package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.BlogArticleEntity;

import java.util.List;
import java.util.UUID;

public interface BlogService {

    List<BlogArticleEntity> getAllArticles();

    BlogArticleEntity getArticleByUuid(UUID uuid);

    UUID saveArticle(BlogArticleEntity article);

    void deleteArticleById(Long id);

    void updateBlogArticle(Long id, BlogArticleEntity updatedBlogArticleEntity);
}
