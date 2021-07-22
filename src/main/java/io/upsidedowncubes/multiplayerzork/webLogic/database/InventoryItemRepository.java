package io.upsidedowncubes.multiplayerzork.webLogic.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface InventoryItemRepository extends CrudRepository<InventoryItemEntity, Integer> {
    List<InventoryItemEntity> findAllByUsername(String username);

    InventoryItemEntity findByUsernameAndItem(String username, String item);

    void deleteAllByUsername(String username);
}
