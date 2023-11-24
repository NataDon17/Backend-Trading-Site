package ru.skypro.homework.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * an entity class for creating an image for a user and an ad
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "image_id")
    private String id;

    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;
}
