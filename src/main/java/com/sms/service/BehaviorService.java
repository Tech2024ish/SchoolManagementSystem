package com.sms.service;

import com.sms.dao.BehaviorDAO;
import com.sms.model.StudentBehavior;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class BehaviorService {

    @Inject
    private BehaviorDAO behaviorDAO;

    public List<StudentBehavior> getByStudent(int studentId) {
        return behaviorDAO.findByStudent(studentId);
    }

    public void save(StudentBehavior behavior) {
        behaviorDAO.save(behavior);
    }
}
