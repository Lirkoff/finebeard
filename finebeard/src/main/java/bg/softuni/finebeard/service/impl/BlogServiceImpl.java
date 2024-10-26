package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.AddArticleDTO;
import bg.softuni.finebeard.model.entity.BlogArticleEntity;
import bg.softuni.finebeard.repository.BlogRepository;
import bg.softuni.finebeard.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Page<BlogArticleEntity> getAllArticles(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }


    @Override
    public BlogArticleEntity getArticleByUuid(UUID uuid) {

        return blogRepository.getBlogArticleEntitiesByUuid(uuid);
    }

    @Override
    public BlogArticleEntity saveArticle(AddArticleDTO addArticleDTO) {
        BlogArticleEntity article = map(addArticleDTO);


        return blogRepository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public void updateBlogArticle(Long id, BlogArticleEntity updatedBlogArticleEntity) {

    }

    public BlogArticleEntity map(AddArticleDTO addArticleDTO) {
        return new BlogArticleEntity()
                .setAuthor(addArticleDTO.author())
                .setTitle(addArticleDTO.title())
                .setContentPath(addArticleDTO.contentPath())
                .setImageUrl(addArticleDTO.imageUrl())
                .setUuid(UUID.randomUUID());
    }
}
