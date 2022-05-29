package com.agent.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="text")
    private String text;
    @Column(name="int_process")
    private String interview_process;
    @Column(name="salary")
    private double salary;
    @ManyToOne
    @JoinColumn(name="jobOffer", referencedColumnName = "id")
    private JobOffer jobOffer;
    @ManyToOne
    @JoinColumn(name="owner", referencedColumnName = "id")
    private User owner;
}
