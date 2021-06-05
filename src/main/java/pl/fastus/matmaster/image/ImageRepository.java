package pl.fastus.matmaster.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tom - 05.06.2021
 */
@Repository
public interface ImageRepository  extends JpaRepository<Image, Long> {
}
