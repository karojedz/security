package com.example.springsecurity.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Person person;

    private String username;
    private String password;
    private boolean accountActivated; //ban, active, activated, timed

    public void setPerson(Person person) {
        this.person = person;
        person.setUser(this);
    }
}
