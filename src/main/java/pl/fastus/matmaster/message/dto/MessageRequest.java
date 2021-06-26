package pl.fastus.matmaster.message.dto;

import lombok.Data;

/**
 * Created by Tom - 26.06.2021
 */
@Data
public class MessageRequest {

    private String name;
    private String sureName;
    private String email;
    private String subject;
    private String textArea;
}
