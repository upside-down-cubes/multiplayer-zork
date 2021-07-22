package io.upsidedowncubes.multiplayerzork.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "InventoryItems")
@Getter
@Setter
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer ID;

    @Column(name = "username")
    private String username;

    @Column(name = "item")
    private String item;

    @Column(name = "quantity")
    private Integer quantity;


    protected InventoryItemEntity() {

    }

    public InventoryItemEntity(String username, String item) {
        this.username = username;
        this.item = item;
        this.quantity = 1;
    }

    @Override
    public String toString() {
        return String.format("Item[Name=%s,Quantity=%d", item, quantity);
    }
}
