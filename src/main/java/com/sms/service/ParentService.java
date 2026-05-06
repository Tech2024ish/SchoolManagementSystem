package com.sms.service;

import com.sms.dao.ParentDAO;
import com.sms.model.Parent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class ParentService {

    @Inject
    private ParentDAO parentDAO;

    public List<Parent> getByStudent(int studentId) {
        return parentDAO.findByStudent(studentId);
    }

    public void save(Parent parent) {
        parentDAO.save(parent);
    }

    public void update(Parent parent) {
        parentDAO.update(parent);
    }
}
