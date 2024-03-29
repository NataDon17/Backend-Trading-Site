package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.homework.dto.authdto.Role;

import javax.persistence.*;
import java.util.*;

/**
 * class-entity for creating a user
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false, length = 32)
    private String email;

    @Column(nullable = false, length = 16)
    private String firstName;

    @Column(nullable = false, length = 16)
    private String lastName;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 32)
    private String username;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ad> AdList = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> commentsList = new ArrayList<>();

    public User(int id, String email, String firstName, String lastName, String phone, Image image, String password,
                String username, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.image = image;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public User(String password, String username, Role role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }
}
