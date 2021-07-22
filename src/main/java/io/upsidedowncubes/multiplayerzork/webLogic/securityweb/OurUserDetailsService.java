package io.upsidedowncubes.multiplayerzork.webLogic.securityweb;

import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlayerEntity player = playerRepository.findByUsername(username);
        if (player != null) {
//            throw new UsernameNotFoundException("blah");
            return User.withUsername(username).password(player.getEncodedPassword()).roles("USER").build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password!!!");
        }
    }
}
