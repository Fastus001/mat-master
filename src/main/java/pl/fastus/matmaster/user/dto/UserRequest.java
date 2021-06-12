package pl.fastus.matmaster.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Created by Tom - 12.06.2021
 */
@Data
public class UserRequest {

    @Email
    private String login;

    @NotBlank
    private String password;

    @Min(3)
    @Max(15)
    private String name;

    @Min(3)
    @Max(20)
    private String sureName;
}
