package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="text")
    private String text;

    @CreationTimestamp
    @Column(name="created")
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name="book_id")
    private Book book;
}
