package io.upsidedowncubes.multiplayerzork.webLogic.Controller;

import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.SimpleResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    /*
    * This is a test to see if a user access a page with out logging in would it give the and error message.
    * "You are not allowed to access this."
    * else it should return as normal.
    * */
    @GetMapping("/api/test")
    public String test(){
        return "If this message is shown, it means login is successful";
    }



    /*
    * This is login function
    * @return Json
    * example: {"success":true,"message":"Login successful"}
    * */
    @PostMapping("/api/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getPrincipal().equals("anonymousUser")){
                return JsonConvertor.convert( SimpleResponseDTO
                        .builder()
                        .success(false)
                        .message("You are currently login as "+ auth.getName())
                        .build());
            }
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


    /*
    * This is the logout function
    * @return Json
    * example: {"success":true,"message":"Logout successful"}
    * */
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
