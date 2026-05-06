package com.sms.filter;

import com.sms.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();

        if (uri.contains("index.xhtml") || uri.contains("index.faces")
                || uri.contains("jakarta.faces.resource")
                || uri.startsWith(ctx + "/images/")
                || uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg")
                || uri.endsWith(".gif") || uri.endsWith(".ico") || uri.endsWith(".webp")
                || uri.equals(ctx + "/") || uri.equals(ctx)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (user == null) {
            res.sendRedirect(ctx + "/index.xhtml");
            return;
        }

        String path = uri.replace(ctx, "");

        if (path.startsWith("/profile")) {
            chain.doFilter(request, response);
            return;
        }

        switch (user.getUserType()) {
            case Admin:
                if (!path.startsWith("/admin/")) {
                    res.sendRedirect(ctx + "/admin/dashboard.xhtml");
                    return;
                }
                break;
            case Teacher:
                if (!path.startsWith("/teacher/")) {
                    res.sendRedirect(ctx + "/teacher/dashboard.xhtml");
                    return;
                }
                break;
            case Student:
                if (!path.startsWith("/student/")) {
                    res.sendRedirect(ctx + "/student/dashboard.xhtml");
                    return;
                }
                break;
            case Parent:
                if (!path.startsWith("/parent/")) {
                    res.sendRedirect(ctx + "/parent/dashboard.xhtml");
                    return;
                }
                break;
        }

        chain.doFilter(request, response);
    }
}
