package io.upsidedowncubes.multiplayerzork.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Integer> {
    InventoryEntity findByUsername(String username);
}
