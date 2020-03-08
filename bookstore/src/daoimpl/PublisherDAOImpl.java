package daoimpl;

import dao.GenericDAO;
import entity.BookEntity;
import entity.PublisherEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class PublisherDAOImpl extends GenericDAO<PublisherEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<PublisherEntity> getByName(String name) {
        TypedQuery<PublisherEntity> query = getSession().createQuery(
                "SELECT e FROM PublisherEntity e " +
                        "WHERE LOWER(e.publisherName) LIKE LOWER(:name)")
                .setParameter("name", '%' + name + '%');
        return query.getResultList();
    }
}
