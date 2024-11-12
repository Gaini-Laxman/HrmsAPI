package com.javafullstackguru.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "performance_review")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "review_period", nullable = false)
    private String reviewPeriod; // e.g., "Q1 2024"

    @Column(name = "score", nullable = false)
    private Double score; // e.g., a score out of 10

    @Column(name = "comments", length = 1000)
    private String comments; // Additional comments about the performance
}
