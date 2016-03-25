package store.snapshot.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao <T> {
	boolean create(final T t);
    T getById(final Serializable id);
    void update(final T t);   
    void delete(final T t);
    List<T> getAllRecords();
}
