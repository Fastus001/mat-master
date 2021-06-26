package pl.fastus.matmaster.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tom - 26.06.2021
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
