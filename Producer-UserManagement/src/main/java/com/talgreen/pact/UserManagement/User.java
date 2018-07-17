package com.talgreen.pact.UserManagement;

import java.util.List;

public class User {
    private String id;
    private String username;
    private List<String> roles;

    public User(String id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
