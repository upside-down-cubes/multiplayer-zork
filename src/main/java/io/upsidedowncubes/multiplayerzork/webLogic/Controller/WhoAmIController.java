package io.upsidedowncubes.multiplayerzork.webLogic.Controller;

import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.WhoAmIDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * A Controller to retrieve currently logged-in user.
 *  */
@RestController
public class WhoAmIController {


    /*
     * This API takes no argument
     * @return Json
     * example: {"loggedIn":true,"username":"archer"} or {"loggedIn":false}
     * */

    @GetMapping("/api/whoami")
    public String whoami() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getPrincipal().equals("anonymousUser")) {
                // if loggedIn
                return JsonConvertor.convert(WhoAmIDTO.builder()
                        .loggedIn(true).username(auth.getName()).build());
            } else {
                return JsonConvertor.convert(WhoAmIDTO.builder()
                        .loggedIn(false)
                        .build());
            }
        } catch (Exception e) {
            return JsonConvertor.convert(WhoAmIDTO.builder().loggedIn(false).build());
        }


    }

}
