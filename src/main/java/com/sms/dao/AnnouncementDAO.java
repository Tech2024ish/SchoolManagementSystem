package com.sms.dao;

import com.sms.model.Announcement;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AnnouncementDAO extends GenericDAO<Announcement> {

    public AnnouncementDAO() {
        super(Announcement.class);
    }

    @Override
    public List<Announcement> findAll() {
        return query(em -> em.createQuery(
            "SELECT a FROM Announcement a ORDER BY a.date DESC", Announcement.class)
            .getResultList());
    }

    public List<Announcement> findByTargetGroup(String targetGroup) {
        return query(em -> em.createQuery(
            "SELECT a FROM Announcement a WHERE a.targetGroup = :tg OR a.targetGroup = 'All' ORDER BY a.date DESC",
            Announcement.class)
            .setParameter("tg", Announcement.TargetGroup.valueOf(targetGroup))
            .getResultList());
    }
}
