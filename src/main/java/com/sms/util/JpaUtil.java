package com.sms.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final EntityManagerFactory EMF = buildFactory();

    private JpaUtil() {}

    private static EntityManagerFactory buildFactory() {
        Map<String, String> overrides = new HashMap<>();
        putIfPresent(overrides, "jakarta.persistence.jdbc.url",      env("DB_URL"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.user",     env("DB_USER"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.password", env("DB_PASSWORD"));
        return Persistence.createEntityManagerFactory("SMSPU", overrides);
    }

    private static String env(String name) {
        String v = System.getenv(name);
        if (v == null || v.isBlank()) v = System.getProperty(name);
        return (v == null || v.isBlank()) ? null : v;
    }

    private static void putIfPresent(Map<String, String> map, String key, String value) {
        if (value != null) map.put(key, value);
    }

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        if (EMF.isOpen()) EMF.close();
    }
}
