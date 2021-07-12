//package io.upsidedowncubes.multiplayerzork.webLogic.database;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Players")
//@Getter
//public class Player {
//
//    @Id
//    @Column(name = "username")
//    @Setter
//    private String username;
//
//    @Column(name = "encoded_password")
//    private String encodedPassword;
//
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "inventory_id")
//    @Setter
//    private Integer inventoryID;
//
//    @Column(name = "session_id")
//    @Setter
//    private Integer sessionID;
//
//    protected Player() {
//
//    }
//
//    public Player(String username, String password) {
//        this.username = username;
//        // might need to edit this part later (creating new instance of BCryptPasswordEncoder)
//        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
//    }
//
//    public void setEncodedPassword(String password) {
//        this.encodedPassword = new BCryptPasswordEncoder().encode(password);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Customer[username=%s, status='%s']", username, sessionID != null ? "in game" : "not in game");
//    }
//}
