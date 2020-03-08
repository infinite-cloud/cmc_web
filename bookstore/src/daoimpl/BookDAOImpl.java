package daoimpl;

import dao.GenericDAO;
import entity.BookEntity;
import entity.CoverTypeEntity;
import entity.GenreEntity;
import entity.PublisherEntity;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;

public class BookDAOImpl extends GenericDAO<BookEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPublicationDate(Date startDate, Date endDate) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.publicationDate " +
                        "BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate, TemporalType.DATE)
                .setParameter("endDate", endDate, TemporalType.DATE);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByName(String name) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE LOWER(e.bookName) LIKE LOWER(:name)")
                .setParameter("name", '%' + name + '%');
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPageCount(Integer pagesMin, Integer pagesMax) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.pageCount BETWEEN :pagesMin AND :pagesMax")
                .setParameter("pagesMin", pagesMin)
                .setParameter("pagesMax", pagesMax);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPrice(Double priceMin, Double priceMax) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.bookPrice BETWEEN :priceMin AND :priceMax")
                .setParameter("priceMin", priceMin)
                .setParameter("priceMax", priceMax);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByAvailability(Boolean isAvailable) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE (e.availableCount = 0 OR :isAvailable IS TRUE) " +
                        "AND NOT (e.availableCount = 0 AND :isAvailable IS TRUE)")
                .setParameter("isAvailable", isAvailable);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPublisherId(Long publisherId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN PublisherEntity p " +
                        "ON e.publisherId = p " +
                        "AND e.publisherId.publisherId = :publisherId")
                .setParameter("publisherId", publisherId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByGenreId(Long genreId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN GenreEntity p " +
                        "ON e.genreId = p AND e.genreId.genreId = :genreId")
                .setParameter("genreId", genreId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByCoverTypeId(Long coverTypeId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN CoverTypeEntity p " +
                        "ON e.coverTypeId = p " +
                        "AND e.coverTypeId.coverTypeId = :coverTypeId")
                .setParameter("coverTypeId", coverTypeId);
        return query.getResultList();
    }
}
