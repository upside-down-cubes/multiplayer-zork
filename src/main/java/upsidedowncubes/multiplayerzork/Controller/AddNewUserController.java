package upsidedowncubes.multiplayerzork.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import upsidedowncubes.multiplayerzork.Controller.utils.JsonConvertor;
import upsidedowncubes.multiplayerzork.Controller.utils.SimpleResponseDTO;
import upsidedowncubes.multiplayerzork.player.SignUp;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AddNewUserController {
    /*
     * This is login function
     * @return Json
     * example: {"success":true,"message":"successful"}
     * */
    @PostMapping("/api/signup")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            SignUp.createUser(username, password);
            return JsonConvertor.convert( SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("successful")
                    .build());
        } catch (AuthenticationException e) {
            return JsonConvertor.convert(SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message("Username has been taken")
                    .build());
        }
    }
}
