package daoimpl;

import dao.GenericDAO;
import entity.AuthorEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDAOImpl extends GenericDAO<AuthorEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<AuthorEntity> getByName(String name) {
        TypedQuery<AuthorEntity> query = getSession().createQuery(
                "SELECT e FROM AuthorEntity e " +
                        "WHERE e.authorName LIKE :name")
                .setParameter("name", '%' + name + '%');
        return query.getResultList();
    }
}
