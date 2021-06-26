package pl.fastus.matmaster.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.matmaster.message.dto.MessageRequest;

/**
 * Created by Tom - 26.06.2021
 */
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository repository;
    private final MessageMapper mapper;

    public Long createMessage(MessageRequest request) {
        final Message messageToSave = mapper.toMessage(request);

        return repository.save(messageToSave).getId();
    }
}
