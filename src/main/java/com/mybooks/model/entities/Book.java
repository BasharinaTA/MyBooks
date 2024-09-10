package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_start")
    private Date dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_finish")
    private Date dateFinish;

    @Column(name = "type")
//    @Enumerated(EnumType.STRING)
    private String type;

    @CreationTimestamp
    @Column(name="created")
    private Date created;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(mappedBy="book", cascade=CascadeType.ALL)
    private Comment comment;
}
