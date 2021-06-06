package pl.fastus.matmaster.blogpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tom - 06.06.2021
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
