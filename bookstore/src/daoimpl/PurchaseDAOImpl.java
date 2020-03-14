package daoimpl;

import dao.GenericDAO;
import entity.PurchaseEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class PurchaseDAOImpl extends GenericDAO<PurchaseEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<PurchaseEntity> getRelevant() {
        TypedQuery<PurchaseEntity> query = getSession().createQuery(
                "SELECT e FROM PurchaseEntity e " +
                        "WHERE LOWER(CAST(e.orderStatus AS string)) != " +
                        "'поставлен' ORDER BY e.orderDate DESC");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseEntity> getByUserId(Long userId) {
        TypedQuery<PurchaseEntity> query = getSession().createQuery(
                "SELECT e FROM PurchaseEntity e " +
                        "WHERE e.userId.userId = :userId " +
                        "ORDER BY e.orderDate DESC")
                .setParameter("userId", userId);
        return query.getResultList();
    }
}
