package upsidedowncubes.multiplayerzork.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import upsidedowncubes.multiplayerzork.Controller.utils.JsonConvertor;
import upsidedowncubes.multiplayerzork.Controller.utils.WhoamiDTO;

/*
* A Controller to retrieve currently logged-in user.
*  */
@RestController
public class WhoamiController {


    @GetMapping("/api/whoami")
    public String whoami(){

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getPrincipal().equals("anonymousUser")) {
                // if loggedIn
                return JsonConvertor.convert(WhoamiDTO.builder()
                        .loggedIn(true).username(auth.getName()).build());
            }
            else {
                return JsonConvertor.convert(WhoamiDTO.builder()
                        .loggedIn(false)
                        .build());
            }
        }catch (Exception e){
            return JsonConvertor.convert(WhoamiDTO.builder().loggedIn(false).build());
        }




    }

}
