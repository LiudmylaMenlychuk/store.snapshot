package store.snapshot.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericDao<T> implements IGenericDao<T> {
	private static final Logger LOG = Logger.getLogger("DAO");
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	public boolean create(final T t) {
		boolean res = false;
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(t);
		    tx.commit();
		    res = true;
		} catch (Exception e) {
			LOG.error("Error in create method " + e);	
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
	            session.close();
	        }
		}
		return res;
	}
	@Override
	public T getById(final Serializable id) {
		Session session = null;
		T obj = null;
		try {
			session = sessionFactory.openSession();
			obj = (T) session.get(type,id);
		} catch(Exception e) {
			LOG.error(e);
		} finally {
			if (session != null && session.isOpen()) {
	            session.close();
	        }
		}
		return obj;
	}
	@Override
	public void update(final T t) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
		} catch(Exception e) {
			LOG.error(e);
		} finally {
			if (session != null && session.isOpen()) {
	            session.close();
	        }
		}
	}
	@Override
	public void delete(final T t) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch(Exception e) {
			LOG.error(e);
		} finally {
			if (session != null && session.isOpen()) {
	               session.close();
	        }
		}
	}
	@Override
	public List<T> getAllRecords() {
		Session session = null;
		List<T> objList = null;
		 try {
	    	   session = sessionFactory.openSession();
	    	   objList = session.createCriteria(type).list();
	       } catch (Exception e) {
	    	   LOG.error(e);
	       } finally {
	           if (session != null && session.isOpen()) {
	               session.close();
	           }
	       }
		return objList;
	}
}
