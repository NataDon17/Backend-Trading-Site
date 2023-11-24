package ru.skypro.homework.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * the entity class for creating the ad
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(length = 32, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Ad(int id, User author, String title, String description, Image image, int price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
