package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
    PlayerEntity findByUsername(String username);
    // gives list of users with session ID (hope it will work)
    List<PlayerEntity> findBySessionID(Integer sessionID);
}