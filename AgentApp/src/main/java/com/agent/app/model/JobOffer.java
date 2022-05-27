package com.agent.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="jobOffers")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="position")
    private String position;
    @Column(name="companyName")
    private String companyName;
    @Column(name="jobDescription")
    private String jobDescription;
    @Column(name="dailyActivities")
    private String dailyActivities;
    @Column(name="candidateRequirements")
    private String candidateRequirements;
    @Column(name="userAPIToken")
    private String userAPIToken;
}
