package pl.fastus.matmaster.message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.fastus.matmaster.message.dto.MessageRequest;

/**
 * Created by Tom - 26.06.2021
 */
@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "textArea", target = "message")
    Message toMessage(MessageRequest request);
}
