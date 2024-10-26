package bg.softuni.finebeard.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AddArticleDTO(@NotEmpty(message = "Enter title!") String title,
                            @NotEmpty(message = "Enter author!") String author,
                            @NotEmpty(message = "Enter content path!") String contentPath,
                            @NotEmpty(message = "Enter imageUrl!") String imageUrl) {

    public static AddArticleDTO empty() {
        return new AddArticleDTO( null,null, null, null);
    }



}
