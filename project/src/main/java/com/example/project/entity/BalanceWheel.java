package com.example.project.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "interests")
public class BalanceWheel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long health; // Шкала от 1 до 10
    private Long career; // Шкала от 1 до 10
    private Long relationships; // Шкала от 1 до 10
    private Long personalDevelopment; // Шкала от 1 до 10
    private Long finances; // Шкала от 1 до 10

    @ManyToOne
    User user;
}
