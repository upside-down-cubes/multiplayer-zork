package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Integer> {
    InventoryEntity findByUsername(String username);
}
