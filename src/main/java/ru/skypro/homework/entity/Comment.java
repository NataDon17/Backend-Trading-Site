package ru.skypro.homework.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * entity class for creating a comment
 */
@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @CreationTimestamp
    @Column(name = "comment_date", length = 20, nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 1000, nullable = false)
    private String text;
}
