package com.agent.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="field")
    private String field;
    @Column(name="description")
    private String description;
    @Column(name="status")
    private Status status;
    @ManyToOne
    @JoinColumn(name="owner", referencedColumnName = "id")
    private User owner;

}
