package authentication;

import daoimpl.AccountDAOImpl;
import entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DBAuthenticationService implements UserDetailsService {
    @Autowired
    private AccountDAOImpl accountDAO;

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        accountDAO.setSession();
        AccountEntity accountEntity = accountDAO.getByEMail(eMail);

        if (accountEntity == null) {
            throw new UsernameNotFoundException(eMail + " not found");
        }

        Boolean isAdmin = accountEntity.isAdmin();
        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + ((isAdmin) ? "ADMIN" : "USER"));

        grantList.add(authority);

        return new User(accountEntity.geteMail(),
                accountEntity.getPasswordHash(),
                grantList);
    }
}
