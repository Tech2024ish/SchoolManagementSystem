package com.sms.dao;

import com.sms.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String username) {
        return query(em -> {
            List<User> results = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
            return results.isEmpty() ? null : results.get(0);
        });
    }

    public List<User> findByUserType(String userType) {
        return query(em -> em.createQuery(
            "SELECT u FROM User u WHERE u.userType = :type", User.class)
            .setParameter("type", User.UserType.valueOf(userType))
            .getResultList());
    }
}
