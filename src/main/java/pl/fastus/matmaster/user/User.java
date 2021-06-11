package pl.fastus.matmaster.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    private String login;

    private String password;

    private String name;
    private String sureName;

    @CreationTimestamp
    private LocalDateTime created;
}
