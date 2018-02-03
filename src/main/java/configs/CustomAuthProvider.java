package configs;

import common.Logged;
import db.dao.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import db.pojo.User;
import utils.MyPasswordEncoder;

import java.sql.SQLException;
import java.util.*;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Logged
    private Logger logger;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            User user = userDao.getUserByLogin(login);
            if (user != null) {
                ArrayList list = new ArrayList();
                String pass = user == null ? null : user.getPassword();
                /*TODO Добавление всех ролей*/
                list.add(new SimpleGrantedAuthority(user.getRole().name()));
                if (myPasswordEncoder.matches(password, pass)) {
                    return new UsernamePasswordAuthenticationToken(login, pass, list);
                }
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
