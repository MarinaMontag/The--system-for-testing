package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"firstName", "lastName", "email", "password", "role"})
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;


    public User(String firstName, String lastName, String email, String password, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if (role == 0)
            this.role = Role.TUTOR;
        else this.role = Role.STUDENT;
    }
    public User(String email, String role){
        this.email = email;
        if(role.equals(Role.TUTOR.toString()))
            this.role=Role.TUTOR;
        else
            this.role=Role.STUDENT;
    }
}
