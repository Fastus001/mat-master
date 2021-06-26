package pl.fastus.matmaster.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fastus.matmaster.message.dto.MessageRequest;

/**
 * Created by Tom - 26.06.2021
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
@RestController
public class MessageController {

    private final MessageService service;

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createMessage(@RequestBody MessageRequest request) {
        final Long savedMessageId = service.createMessage(request);
        log.info("Saved request message with id " + savedMessageId + ".");
        return savedMessageId;
    }
}
