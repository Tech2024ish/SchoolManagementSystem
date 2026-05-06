package com.sms.service;

import com.sms.dao.UserDAO;
import com.sms.model.User;
import org.apache.commons.io.IOUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Named
@ApplicationScoped
public class ImageService {

    @Inject
    private UserDAO userDAO;

    public String saveImage(Part file, int userId, String webappPath) {
        if (file == null || file.getSize() == 0) return null;
        try {
            String fileName = "user_" + userId + "_" + System.currentTimeMillis() + getExtension(file);
            String imagesDir = webappPath + File.separator + "images";
            File dir = new File(imagesDir);
            if (!dir.exists()) dir.mkdirs();
            File dest = new File(imagesDir + File.separator + fileName);
            try (InputStream in = file.getInputStream();
                 OutputStream out = new FileOutputStream(dest)) {
                IOUtils.copy(in, out);
            }
            String imagePath = "/images/" + fileName;
            User user = userDAO.findById(userId);
            if (user != null) {
                user.setImageLink(imagePath);
                userDAO.update(user);
            }
            return imagePath;
        } catch (Exception e) {
            return null;
        }
    }

    private String getExtension(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) return ".jpg";
        for (String token : header.split(";")) {
            if (token.trim().startsWith("filename")) {
                String name = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                int dot = name.lastIndexOf('.');
                return dot >= 0 ? name.substring(dot) : ".jpg";
            }
        }
        return ".jpg";
    }
}
