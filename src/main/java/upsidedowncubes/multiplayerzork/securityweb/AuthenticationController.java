package upsidedowncubes.multiplayerzork.securityweb;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
            request.login(username, password);
            return "Login successful";
        } catch (ServletException e) {
            return "Fail to login";
        }
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        try {
            request.logout();
            return "Logout successful.";
        } catch (ServletException e) {
            return "Fail to logout.";
        }

    }



}
