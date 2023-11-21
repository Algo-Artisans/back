package com.example.docker.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Table(name="hello")
public class Hello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hello_id")
    private Long helloId;

    @NotNull
    @Column(name = "text")
    private String text;

    @Builder
    public Hello(Long helloId, String text) {
        this.helloId=helloId;
        this.text = text;

    }

}
