package com.example.project.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String password;
    private String login;

    @OneToMany
    private List<BalanceWheel> balanceCategories;
    @Column(unique = true)
    private String email;
}
