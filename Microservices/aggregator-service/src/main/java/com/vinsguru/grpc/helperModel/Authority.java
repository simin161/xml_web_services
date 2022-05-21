package com.vinsguru.grpc.helperModel;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class Authority implements GrantedAuthority {
    private String name;

    public Authority(){}

    public Authority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
