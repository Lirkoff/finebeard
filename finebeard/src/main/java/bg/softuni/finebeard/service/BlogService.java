package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.AddArticleDTO;
import bg.softuni.finebeard.model.entity.BlogArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BlogService {

    Page<BlogArticleEntity> getAllArticles(Pageable pageable);

    BlogArticleEntity getArticleByUuid(UUID uuid);

    BlogArticleEntity saveArticle(AddArticleDTO article);

    void deleteArticleById(Long id);

    void updateBlogArticle(Long id, BlogArticleEntity updatedBlogArticleEntity);
}
