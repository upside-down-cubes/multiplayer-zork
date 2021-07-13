package io.upsidedowncubes.multiplayerzork.webLogic.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "Playe")
@Getter
public class PlayerEntity {

    @Id
    @Column(name = "username")
    @Setter
    private String username;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(name = "session_id")
    @Setter
    private Integer sessionID;

    @Column(name = "hp")
    @Setter
    private Integer hp;

    @Column(name = "max_hp")
    @Setter
    private Integer maxHp;

    @Column(name = "attack")
    @Setter
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
    }

    public void setEncodedPassword(String password) {
        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String toString() {
        return String.format("Player[username=%s, status='%s']", username, sessionID != null ? "in game" : "not in game");
    }
}
