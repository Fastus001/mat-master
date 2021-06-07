package pl.fastus.matmaster.blogpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fastus.matmaster.enums.Status;

import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByStatusEquals(Status status);
}
