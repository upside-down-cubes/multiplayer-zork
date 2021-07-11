package upsidedowncubes.multiplayerzork.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;

// TODO
@Component
public class SignUpBean {
    @Autowired
    SignUp signUp;

    @Bean
    // TODO: delete this later (example on how to add user)
    public String addAdmin() {
        try {
            signUp.createUser("admin", "1111");
            return "";
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return " ";
    }
}
