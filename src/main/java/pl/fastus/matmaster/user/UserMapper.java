package pl.fastus.matmaster.user;

import org.mapstruct.Mapper;
import pl.fastus.matmaster.user.dto.UserResponse;

/**
 * Created by Tom - 12.06.2021
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);
}
