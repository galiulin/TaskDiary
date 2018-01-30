package configs;

import dao.outer.UserDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import utils.MyPasswordEncoder;

import java.sql.SQLException;
import java.util.*;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    UserDataDao userDataDao;

    @Autowired
    MyPasswordEncoder myPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            ArrayList list = new ArrayList();
            String pass = userDataDao.getLoginAndPass(name).getPassword();
            list.add(new SimpleGrantedAuthority("role_user"));
            if (myPasswordEncoder.matches(password, pass)) {
                System.out.println("зашли");
                return new UsernamePasswordAuthenticationToken(name, pass, list);
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
