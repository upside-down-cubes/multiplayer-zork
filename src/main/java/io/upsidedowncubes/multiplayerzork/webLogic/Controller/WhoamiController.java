package upsidedowncubes.multiplayerzork.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.WhoamiDTO;

/*
* A Controller to retrieve currently logged-in user.
*  */
@RestController
public class WhoamiController {


    /*
    * This API takes no argument
    * @return Json
    * example: {"loggedIn":true,"username":"archer"} or {"loggedIn":false}
    * */

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
