package upsidedowncubes.multiplayerzork.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import upsidedowncubes.multiplayerzork.database.Player;
import upsidedowncubes.multiplayerzork.database.PlayerRepository;

import javax.inject.Inject;
import javax.security.sasl.AuthenticationException;

@Component
public class SignUp {
    @Autowired
    private PlayerRepository repository;

    public void createUser(String username, String password) throws AuthenticationException {
        if (repository.findByUsername(username) == null) {
            repository.save(new Player(username, password));
        } else {
            throw new AuthenticationException("Username already exists!!!");
        }
    }
}
