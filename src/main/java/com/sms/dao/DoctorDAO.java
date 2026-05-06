package com.sms.dao;

import com.sms.model.Doctor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorDAO extends GenericDAO<Doctor> {
    public DoctorDAO() { super(Doctor.class); }
}
