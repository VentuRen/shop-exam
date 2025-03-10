package com.test.interview.data;

import com.test.interview.models.AppUser;
import com.test.interview.models.Role;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String email;
    private Role role;

    public UserResponse(AppUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}