package com.mybooks.model.entities;

import javax.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(name="password")
    private String password;

    @Column(name="created")
    private Date created;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy="user", cascade=CascadeType.ALL)
    private Profile profile;
}
