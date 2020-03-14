package daoimpl;

import dao.GenericDAO;
import entity.AuthorEntity;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDAOImpl extends GenericDAO<AuthorEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<AuthorEntity> getByName(String name) {
        TypedQuery<AuthorEntity> query = getSession().createQuery(
                "SELECT e FROM AuthorEntity e " +
                        "WHERE LOWER(e.authorName) LIKE LOWER(:name)")
                .setParameter("name", '%' + name + '%');
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public AuthorEntity getByExactName(String name) {
        TypedQuery<AuthorEntity> query;
        try {
            query = getSession().createQuery(
                    "SELECT e FROM AuthorEntity e " +
                            "WHERE LOWER(e.authorName) = LOWER(:name)")
                    .setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
