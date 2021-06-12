package pl.fastus.matmaster.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Created by Tom - 12.06.2021
 */
@Accessors(chain = true)
@Data
public class UserUpdate {

    @NotBlank
    private String password;

    @Min(3)
    @Max(15)
    private String name;

    @Min(3)
    @Max(20)
    private String sureName;
}
