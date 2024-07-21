package com.tosan.tools.tracker.starter.dao;

import com.tosan.tools.tracker.starter.model.TrackerEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author F.Ebrahimi
 * @since 12/10/2023
 */
public class TrackerDao<T extends TrackerEntity<?>> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T save(T entity) {
        entity = doPersist(entity);
        getEntityManager().flush();
        return entity;
    }

    private T doPersist(T entity) {
        try {
            getEntityManager().persist(entity);
            return entity;
        } catch (EntityExistsException e) {
            throw new RuntimeException("entity '" + entity.getClass().getName() + "' already persisted: " + entity);
        }
    }
}
