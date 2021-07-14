package io.upsidedowncubes.multiplayerzork.webLogic.database;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "Players")
@Getter
@Setter
public class PlayerEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "encoded_password")
    @Setter(AccessLevel.NONE)
    private String encodedPassword;

    @Column(name = "session_id")
    private String sessionID;

    @Column(name = "row")
    private int row;

    @Column(name = "col")
    private int col;

    @Column(name = "hp")
    private Integer hp;

    @Column(name = "max_hp")
    private Integer maxHp;

    @Column(name = "attack")
    private Integer attack;

    protected PlayerEntity() {

    }

    public PlayerEntity(String username, String password) {
        this.username = username;
        // might need to edit this part later (creating new instance of BCryptPasswordEncoder)
        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
        this.hp = 50;
        this.maxHp = 50;
        this.attack = 5;
        this.row = -1;
        this.col = -1;
    }

    public void setEncodedPassword(String password) {
        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String toString() {
        return String.format("Player[username=%s, status='%s']", username, sessionID != null ? "in game" : "not in game");
    }
}
