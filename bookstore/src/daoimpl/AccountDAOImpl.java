package daoimpl;

import dao.GenericDAO;
import entity.AccountEntity;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class AccountDAOImpl extends GenericDAO<AccountEntity, Long> {
    @SuppressWarnings("unchecked")
    public AccountEntity getByEMail(String eMail) {
        try {
            TypedQuery<AccountEntity> query = getSession().createQuery(
                    "SELECT e FROM AccountEntity e " +
                            "WHERE LOWER(e.eMail) = LOWER(:eMail)")
                    .setParameter("eMail", eMail);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
