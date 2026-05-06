package com.sms.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static volatile EntityManagerFactory EMF;

    private JpaUtil() {}

    private static synchronized EntityManagerFactory factory() {
        if (EMF == null) {
            EMF = buildFactory();
        }
        return EMF;
    }

    private static EntityManagerFactory buildFactory() {
        Map<String, String> overrides = new HashMap<>();
        putIfPresent(overrides, "jakarta.persistence.jdbc.url",      env("DB_URL"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.user",     env("DB_USER"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.password", env("DB_PASSWORD"));
        try {
            return Persistence.createEntityManagerFactory("SMSPU", overrides);
        } catch (Throwable t) {
            System.err.println("######## JPAUTIL INIT FAILURE - FULL CAUSE CHAIN ########");
            System.err.println("DB_URL  = " + overrides.getOrDefault("jakarta.persistence.jdbc.url", "(from persistence.xml)"));
            System.err.println("DB_USER = " + overrides.getOrDefault("jakarta.persistence.jdbc.user", "(from persistence.xml)"));
            Throwable c = t;
            int depth = 0;
            while (c != null && depth < 20) {
                System.err.println("  [" + depth + "] " + c.getClass().getName() + " :: " + c.getMessage());
                c = c.getCause();
                depth++;
            }
            System.err.println("######## END CHAIN ########");
            t.printStackTrace();
            throw t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
        }
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
        return factory().createEntityManager();
    }

    public static void shutdown() {
        if (EMF != null && EMF.isOpen()) EMF.close();
    }
}
