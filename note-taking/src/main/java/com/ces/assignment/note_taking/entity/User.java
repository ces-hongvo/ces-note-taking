package com.ces.assignment.note_taking.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(
    name = "user",
    indexes = {
        @Index(columnList = "email", name = "email_index")
    }
)
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -7346600593302403360L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;
}
