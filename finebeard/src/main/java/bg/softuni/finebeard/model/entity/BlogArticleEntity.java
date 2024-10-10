package bg.softuni.finebeard.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity(name = "blog_articles")
public class BlogArticleEntity extends BaseEntity{

    @JdbcTypeCode(Types.VARCHAR)
    @NotNull
    private UUID uuid;

    private String title;


    private String author;

    private String content;


    private String imageUrl;


    public UUID getUuid() {
        return uuid;
    }

    public BlogArticleEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlogArticleEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BlogArticleEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogArticleEntity setContent(String content) {
        this.content = content;
        return this;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public BlogArticleEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


}
