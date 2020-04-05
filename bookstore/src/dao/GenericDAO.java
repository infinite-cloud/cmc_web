package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {
    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> persistentClass;
    private Session session;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setSession() {
        this.session = sessionFactory.getCurrentSession();
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
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        criteria.from(persistentClass);
        List<T> data = getSession().createQuery(criteria).getResultList();
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
