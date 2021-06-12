package pl.fastus.matmaster.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created by Tom - 12.06.2021
 */
@Accessors(chain = true)
@Data
public class UserResponse {

    private String login;
    private String password;
    private String name;
    private String sureName;
    private LocalDateTime created;
}
