package upsidedowncubes.multiplayerzork.securityweb;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OurUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public OurUserDetailsService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)){
            return User.withUsername(username).password(passwordEncoder.encode("1111")).roles("USER").build();
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password !!!");
        }
    }
}
