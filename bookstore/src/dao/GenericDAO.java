package dao;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {
    private Class<T> persistentClass;
    private Session session;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setSession(Session session) {
        this.session = session;
    }

    protected Session getSession() {
        return session;
    }

    public T getById(ID id) {
        return getSession().get(persistentClass, id);
    }

    public List<T> getAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        criteria.from(persistentClass);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void delete(T t) {
        getSession().delete(t);
    }

    public void save(T t) {
        getSession().save(t);
    }

    public void update(T t) {
        getSession().update(t);
    }
}
