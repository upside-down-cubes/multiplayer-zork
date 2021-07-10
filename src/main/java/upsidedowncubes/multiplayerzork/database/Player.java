package upsidedowncubes.multiplayerzork.database;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "Players")
public class Player {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id")
    private Integer inventoryID;

    @Column(name = "session_id")
    private Integer sessionID;

    protected Player() {

    }

    public Player(String username, String password) {
        this.username = username;
        // might need to edit this part later (creating new instance of BCryptPasswordEncoder)
        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String password) {
        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
    }

    public Integer getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String toString() {
        return String.format("Customer[username=%s, status='%s']", username, sessionID != null ? "in game" : "not in game");
    }
}
