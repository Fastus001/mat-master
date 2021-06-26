package pl.fastus.matmaster.message;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

/**
 * Created by Tom - 26.06.2021
 */
@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String sureName;
    private String email;
    private String subject;

    @Lob
    private String message;

    @CreationTimestamp
    private LocalDateTime creationTime;
}
