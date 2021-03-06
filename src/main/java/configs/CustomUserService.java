package configs;

import db.dao.UserDAO;
import db.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    private UserInside userInside;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        try {
            db.pojo.User userData = userDAO.getUserByLogin(login);

            Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
            grantedAuthoritySet.add(new SimpleGrantedAuthority(userData.getRole().name()));

            userInside = new UserInside(userData.getLogin(), userData.getPassword(), grantedAuthoritySet, userData.getId());

        } catch (DAOException e) {
            throw new UsernameNotFoundException("пользователь не найден", e);
        }
        return userInside;
    }


    public UserInside getUserInside() {
        return userInside;
    }
}
