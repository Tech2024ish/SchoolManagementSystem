package com.sms.dao;

import com.sms.model.Nurse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NurseDAO extends GenericDAO<Nurse> {
    public NurseDAO() { super(Nurse.class); }
}
