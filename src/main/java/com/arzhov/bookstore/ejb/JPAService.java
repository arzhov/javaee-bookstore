package com.arzhov.bookstore.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class JPAService<T> {
    private final Class<T> entityClass;

    public JPAService(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(final T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(final T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(final T entity) {
        getEntityManager().remove(entity);
    }

    public abstract T find(Long id);
    
	public List<T> findAll() {
        final javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public abstract List<T> findRange(int[] range);

    public int count() {
        final CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        final Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        final javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }            
}
