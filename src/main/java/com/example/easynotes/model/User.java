package com.example.easynotes.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Transient
    @Column(nullable = true)
    private String name;
    @Transient
    public String getName() {
        return name;
    }
    @Transient
    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @Column(nullable = true)
    private String age;

    @Transient
    public String getAge() {
        return age;
    }
    @Transient
    public void setAge(String age) {
        this.age = age;
    }

}
