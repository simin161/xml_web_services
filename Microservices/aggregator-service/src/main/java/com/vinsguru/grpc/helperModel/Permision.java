package com.vinsguru.grpc.helperModel;

import org.springframework.security.core.GrantedAuthority;

public class Permision implements GrantedAuthority {
    private String name;

    public Permision(){}

    public Permision(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
