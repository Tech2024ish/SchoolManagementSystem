package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Doctors")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DoctorId")
    private int doctorId;

    @Column(name = "FirstName", length = 50)
    private String firstName;

    @Column(name = "LastName", length = 50)
    private String lastName;

    @Column(name = "Telephone", length = 20)
    private String telephone;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "HospitalName", length = 100)
    private String hospitalName;

    public Doctor() {}

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int v) { this.doctorId = v; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String v) { this.telephone = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getAddress() { return address; }
    public void setAddress(String v) { this.address = v; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String v) { this.hospitalName = v; }
}
