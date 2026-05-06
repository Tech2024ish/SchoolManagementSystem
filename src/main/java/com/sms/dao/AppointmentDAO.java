package com.sms.dao;

import com.sms.model.Appointment;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AppointmentDAO extends GenericDAO<Appointment> {

    public AppointmentDAO() {
        super(Appointment.class);
    }

    @Override
    public List<Appointment> findAll() {
        return query(em -> em.createQuery(
            "SELECT a FROM Appointment a ORDER BY a.date DESC", Appointment.class)
            .getResultList());
    }

    public List<Appointment> findByUser(int userId) {
        return query(em -> em.createQuery(
            "SELECT a FROM Appointment a WHERE a.requestedBy.userId = :uid ORDER BY a.date DESC",
            Appointment.class)
            .setParameter("uid", userId)
            .getResultList());
    }
}
