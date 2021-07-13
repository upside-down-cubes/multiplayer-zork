package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends CrudRepository<InventoryItemEntity, Integer> {
    InventoryItemEntity findByID(Integer ID);
    List<InventoryItemEntity> findAllByUsername(String username);
    InventoryItemEntity findByUsernameAndItem(String username, String item);
}
