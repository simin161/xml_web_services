package com.agent.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="VerificationCode")
public class VerificationCode {
    @GeneratedValue
    @Id
    private Long id;
    @Column(name="code")
    private String code;
    @Column(name="dateOfCreation")
    private LocalDateTime dateOfCreation;

    public VerificationCode() {}
    public VerificationCode(String code){
        this.code = code;
        this.dateOfCreation = LocalDateTime.now();
    }
}
