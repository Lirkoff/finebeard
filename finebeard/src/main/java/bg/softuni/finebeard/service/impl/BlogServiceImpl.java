package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.BlogArticleEntity;
import bg.softuni.finebeard.repository.BlogRepository;
import bg.softuni.finebeard.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<BlogArticleEntity> getAllArticles() {
        return blogRepository.findAll();
    }


    @Override
    public BlogArticleEntity getArticleByUuid(UUID uuid) {

        return blogRepository.getBlogArticleEntitiesByUuid(uuid);
    }

    @Override
    public UUID saveArticle(BlogArticleEntity article) {
        article.setUuid(UUID.randomUUID());

        return blogRepository.save(article).getUuid();
    }

    @Override
    public void deleteArticleById(Long id) {

    }

    @Override
    public void updateBlogArticle(Long id, BlogArticleEntity updatedBlogArticleEntity) {

    }
}
