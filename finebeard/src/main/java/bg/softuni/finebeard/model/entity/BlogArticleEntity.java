package bg.softuni.finebeard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "blog_articles")
public class BlogArticleEntity extends BaseEntity{

    @JdbcTypeCode(Types.VARCHAR)
    @NotNull
    private UUID uuid;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String contentPath;

    @NotEmpty
    private String imageUrl;


    public @NotNull UUID getUuid() {
        return uuid;
    }

    public BlogArticleEntity setUuid(@NotNull UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public BlogArticleEntity setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    public @NotNull String getAuthor() {
        return author;
    }

    public BlogArticleEntity setAuthor(@NotNull String author) {
        this.author = author;
        return this;
    }

    public @NotEmpty String getContentPath() {
        return contentPath;
    }

    public BlogArticleEntity setContentPath(@NotEmpty String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public @NotNull String getImageUrl() {
        return imageUrl;
    }

    public BlogArticleEntity setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
