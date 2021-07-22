package io.upsidedowncubes.multiplayerzork.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Inventories")
@Getter
@Setter
public class InventoryEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "current_load")
    private Integer currentLoad;

    protected InventoryEntity() {

    }

    public InventoryEntity(String username) {
        this.username = username;
        this.capacity = 20;
        this.currentLoad = 0;
    }

    @Override
    public String toString() {
        return String.format("Load: %d/%d", currentLoad, capacity);
    }
}
