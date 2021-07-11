package io.upsidedowncubes.multiplayerzork.webLogic.securityweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import io.upsidedowncubes.multiplayerzork.webLogic.database.Player;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private PlayerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = repository.findByUsername(username);
        if (player != null){
            return User.withUsername(username).password(player.getEncodedPassword()).roles("USER").build();
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password!!!");
        }
    }
}
