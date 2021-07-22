package io.upsidedowncubes.multiplayerzork.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
    PlayerEntity findByUsername(String username);

    Set<PlayerEntity> findAllBySessionIDAndRowAndCol(String sessionID, int row, int col);
}