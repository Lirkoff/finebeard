package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogArticle, Long>{
}
