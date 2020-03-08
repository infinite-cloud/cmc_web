package daoimpl;

import dao.GenericDAO;
import entity.OrderedBookEntity;
import entity.OrderedBookId;

import javax.persistence.TypedQuery;
import java.util.List;

public class OrderedBookDAOImpl
        extends GenericDAO<OrderedBookEntity, OrderedBookId> {
    public OrderedBookEntity getByCompositeId(Long orderId, Long bookId) {
        return getById(new OrderedBookId(orderId, bookId));
    }

    @SuppressWarnings("unchecked")
    public List<OrderedBookEntity> getByOrderId(Long orderId) {
        TypedQuery<OrderedBookEntity> query = getSession().createQuery(
                "SELECT e FROM OrderedBookEntity e " +
                        "WHERE e.id.orderId = :orderId")
                .setParameter("orderId", orderId);
        return query.getResultList();
    }
}
