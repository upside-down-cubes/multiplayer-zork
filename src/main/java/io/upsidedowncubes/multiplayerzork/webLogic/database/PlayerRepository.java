package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
    PlayerEntity findByUsername(String username);
    Set<PlayerEntity> findAllBySessionIDAndRowAndCol(String sessionID, int row, int col);
}