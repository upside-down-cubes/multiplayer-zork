package io.upsidedowncubes.multiplayerzork.webLogic.securityweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/api/login", "/api/logout","/api/signup", "/api/whoami").permitAll();

        // permit all OPTIONS requests
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        // Handle error output as JSON for unauthorized access
        http.exceptionHandling()
                .authenticationEntryPoint(new JsonHttp403ForbiddenEntyPoint());

        // Set every Other path to require authentication
        http.authorizeRequests().antMatchers("/**").authenticated();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return ourUserDetailsService;
    }

    class JsonHttp403ForbiddenEntyPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException e) throws IOException, ServletException {

            // output JSON message
            // for now just print string
            response.getWriter().println("You are not allowed to access this.");
        }
    }
}
