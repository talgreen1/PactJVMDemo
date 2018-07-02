package com.talgreen.pact.UserManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserManagementMappings {

    List<User> users;

    public UserManagementMappings() {
        users = new ArrayList<>();
        users.add(new User("1", "talgreen", "admin"));
        users.add(new User("2","ClarkKent", "user"));
    }

    @RequestMapping("/user/{id}")
    public ResponseEntity<User> getUsers(@PathVariable String id){
        for (User user : users) {
            if (user.getId().equals(id)){
                return ResponseEntity.ok().body(user);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
