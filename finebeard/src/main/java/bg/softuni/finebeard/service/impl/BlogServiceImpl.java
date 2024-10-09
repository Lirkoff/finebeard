package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.BlogArticle;
import bg.softuni.finebeard.repository.BlogRepository;
import bg.softuni.finebeard.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlogServiceImpl implements BlogService {
    private BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<BlogArticle> getAllArticles() {
        return List.of();
    }


    @Override
    public BlogArticle getArticleById(Long id) {
        return null;
    }

    @Override
    public BlogArticle saveArticle(BlogArticle article) {
        return null;
    }

    @Override
    public void deleteArticleById(Long id) {

    }

    @Override
    public void updateBlogArticle(Long id, BlogArticle updatedBlogArticle) {

    }
}
