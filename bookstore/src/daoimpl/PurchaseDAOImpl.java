package daoimpl;

import dao.GenericDAO;
import entity.BookEntity;
import entity.OrderedBookEntity;
import entity.PurchaseEntity;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;

public class PurchaseDAOImpl extends GenericDAO<PurchaseEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<PurchaseEntity> getRelevant() {
        TypedQuery<PurchaseEntity> query = getSession().createQuery(
                "SELECT e FROM PurchaseEntity e " +
                        "WHERE e.orderStatus != " +
                        "'DELIVERED' ORDER BY e.orderDate DESC");
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

    @SuppressWarnings("unchecked")
    public void save(PurchaseEntity purchase, List<Pair<BookEntity, Integer>> books) {
        if (books.isEmpty()) {
            return;
        }

        Session session = getSession();
        OrderedBookDAOImpl orderedBookDAO = new OrderedBookDAOImpl();
        orderedBookDAO.setSession(session);

        Transaction tx = session.beginTransaction();

        this.save(purchase);

        for (Pair<BookEntity, Integer> book : books) {
            orderedBookDAO.save(new OrderedBookEntity(book.getValue(), purchase, book.getKey()));
        }

        tx.commit();
    }
}
