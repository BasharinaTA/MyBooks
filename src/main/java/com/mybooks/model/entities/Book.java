package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_finish")
    private LocalDate dateFinish;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @CreationTimestamp
    @Column(name="created")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(mappedBy="book", cascade=CascadeType.ALL)
    private Comment comment;

    public String getType() {
        return type.getDescription();
    }

    public void setType(String type) {
        this.type = Type.fromDescription(type);
    }
}
