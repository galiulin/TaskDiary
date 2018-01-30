package configs;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserInside extends User {
    private int user_id;


    public UserInside(String username, String password, Collection<? extends GrantedAuthority> authorities, int user_id) {
        super(username, password, authorities);
        this.user_id = user_id;

    }

    public int getUser_id() {
        return user_id;
    }

}
