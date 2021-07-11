package io.upsidedowncubes.multiplayerzork.webLogic.securityweb;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonHttp403ForbiddenEntyPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        // output JSON message
        // for now just print string
        response.getWriter().println("You are not allowed to access this.");
    }
}