package pl.fastus.matmaster.paragraph;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tom - 06.06.2021
 */
@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
}
