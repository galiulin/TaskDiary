package configs;

import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pojo.User;
import utils.MyPasswordEncoder;

import java.sql.SQLException;
import java.util.*;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    UserDAO userDao;

    @Autowired
    MyPasswordEncoder myPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            ArrayList list = new ArrayList();
            User user = userDao.getUserByLogin(login);
            String pass = user == null ? null : user.getPassword();
//            String pass = userDao.getUserByLogin(login).getPassword();
            list.add(new SimpleGrantedAuthority("role_user"));
            if (myPasswordEncoder.matches(password, pass)) {
                return new UsernamePasswordAuthenticationToken(login, pass, list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
