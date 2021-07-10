package upsidedowncubes.multiplayerzork.securityweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import upsidedowncubes.multiplayerzork.utils.JsonConvertor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @GetMapping("/api/test")
    public String test(){
        return "If this message is shown, it means login is successful";
    }



    @PostMapping("/api/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            request.logout();
            request.login(username, password);
            return JsonConvertor.convert( SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("Login successful")
                    .build());
        } catch (ServletException e) {
            return JsonConvertor.convert(SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message("Fail to login")
            .build());
        }
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        try {
            request.logout();
            return JsonConvertor.convert( SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("Logout successful")
                    .build());
        } catch (ServletException e) {
            return JsonConvertor.convert( SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message("Fail to logout.")
                    .build());

        }

    }



}
