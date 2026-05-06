package com.sms.dao;

import com.sms.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.function.Function;

public abstract class GenericDAO<T> {

    private final Class<T> entityClass;

    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager em() {
        return JpaUtil.em();
    }

    protected <R> R query(Function<EntityManager, R> work) {
        EntityManager em = em();
        try {
            return work.apply(em);
        } finally {
            em.close();
        }
    }

    protected <R> R inTx(Function<EntityManager, R> work) {
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            R result = work.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void save(T entity) {
        inTx(em -> { em.persist(entity); return null; });
    }

    public T update(T entity) {
        return inTx(em -> em.merge(entity));
    }

    public void delete(T entity) {
        inTx(em -> {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            return null;
        });
    }

    public T findById(int id) {
        return query(em -> em.find(entityClass, id));
    }

    public List<T> findAll() {
        String name = entityClass.getSimpleName();
        return query(em -> em.createQuery("SELECT e FROM " + name + " e", entityClass).getResultList());
    }
}
