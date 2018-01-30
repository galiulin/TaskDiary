package configs;

import dao.outer.UserDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pojo.outer.UserData;

import java.sql.SQLException;
import java.util.Arrays;

@Component
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDataDao userDataDao;

    public UserInside userInside;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            UserData userData = userDataDao.getLoginAndPass(username);
            GrantedAuthority[] grantedAuthorities = new GrantedAuthority[1];
            grantedAuthorities[0] = new SimpleGrantedAuthority("role_user");
            userInside = new UserInside(userData.getLogin(), userData.getPassword(),
                    Arrays.asList(grantedAuthorities), userDataDao.getIdByName(username));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInside;
    }


    public UserInside getUserInside() {
        return userInside;
    }
}
