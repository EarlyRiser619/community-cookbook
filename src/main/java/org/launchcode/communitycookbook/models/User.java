package org.launchcode.communitycookbook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=4, max=20)
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    public User() {}

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }







    public int getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
