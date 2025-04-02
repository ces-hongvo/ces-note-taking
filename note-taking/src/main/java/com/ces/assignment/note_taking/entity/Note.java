package com.ces.assignment.note_taking.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
    name = "note"
)
public class Note implements Serializable {

    @Serial
    private static final long serialVersionUID = 5408074924661366518L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private long noteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "parent_note_id", nullable = true)
    private long parentNoteId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
