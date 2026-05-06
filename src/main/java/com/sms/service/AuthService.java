package com.sms.service;

import com.sms.dao.UserDAO;
import com.sms.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.faces.context.FacesContext;

@Named
@ApplicationScoped
public class AuthService {

    @Inject
    private UserDAO userDAO;

    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) return false;
        if (!BCrypt.checkpw(password, user.getPassword())) return false;
        HttpSession session = getSession(true);
        session.setAttribute("loggedUser", user);
        return true;
    }

    public void logout() {
        HttpSession session = getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public User getCurrentUser() {
        HttpSession session = getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("loggedUser");
    }

    public String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(10));
    }

    private HttpSession getSession(boolean create) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) return null;
        return (HttpSession) fc.getExternalContext().getSession(create);
    }
}
