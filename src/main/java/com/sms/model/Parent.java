package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Parents")
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParentId")
    private int parentId;

    @Column(name = "FirstName", length = 50)
    private String firstName;

    @Column(name = "LastName", length = 50)
    private String lastName;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Telephone", length = 20)
    private String telephone;

    @Column(name = "Location", length = 255)
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentID")
    private Student student;

    public Parent() {}

    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
