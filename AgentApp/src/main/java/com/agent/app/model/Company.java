package com.agent.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
public class Company {

    @Id
    private String id;
    private String name;
    private User owner;

}
