package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="hash_password")
    private String hashPassword;

    @CreationTimestamp
    @Column(name="created")
    private LocalDateTime created;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy="user", cascade=CascadeType.ALL)
    private Profile profile;
}
