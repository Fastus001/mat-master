package pl.fastus.matmaster.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String login;

    private String password;

    private String name;
    private String sureName;

    private String role;

    @CreationTimestamp
    private LocalDateTime created;

    @Builder
    public User(String login, String password, String name, String sureName, String role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.sureName = sureName;
        this.role = role;
    }
}
