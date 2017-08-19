package com.invoicebinder.server.dataaccess;

import java.io.Serializable;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public abstract class BaseDAO<E,K> {
	protected Class<E> entityClass;

	@Autowired
	@Qualifier("sessionFactoryWebInvoice") protected SessionFactory sf;


	public BaseDAO(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

        public BaseDAO() {
		this.entityClass = null;
	}
                
	public SessionFactory getSessionFactory() {
		return sf;
	}

	@SuppressWarnings("unchecked")
	public K save(E entity) {
		return (K) sf.getCurrentSession().save(entity);
	}

	public void update(E entity) {
		sf.getCurrentSession().update(entity);
	}

	public void delete(E entity) {
		// System.out.println("deleting " + entity.getClass().getSimpleName());
		sf.getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public E find(K id) {
		// System.out.println("finding " + object + " with ID " + id);
		return (E) sf.getCurrentSession().get(entityClass, (Serializable) id);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return sf.getCurrentSession()
				.createQuery("from " + entityClass.getSimpleName())
				.list();
	}

}
