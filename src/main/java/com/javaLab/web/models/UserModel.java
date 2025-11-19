package com.javaLab.web.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private byte[] avatar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role userRole;

}


