package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="text")
    private String text;

    @Column(name="rate")
    private boolean rate;

    @Column(name="created")
    private Date created;

    @OneToOne
    @JoinColumn(name="book_id")
    private Book book;

//    @ManyToOne
//    @JoinColumn(name="profile_id")
//    private Profile profile;
}
