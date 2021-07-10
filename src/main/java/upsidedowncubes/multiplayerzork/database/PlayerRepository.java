package upsidedowncubes.multiplayerzork.database;

import org.springframework.data.repository.CrudRepository;


public interface PlayerRepository extends CrudRepository<Player, String> {
    Player findByUsername(String username);
    Player findBySessionID(Integer sessionID);
}