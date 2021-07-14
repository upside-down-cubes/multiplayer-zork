package io.upsidedowncubes.multiplayerzork.webLogic.Controller;

import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.JsonConvertor;
import io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils.SimpleResponseDTO;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerEntity;
import io.upsidedowncubes.multiplayerzork.webLogic.database.PlayerRepository;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AddNewUserController {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    private void createUser(String username, String password) throws AuthenticationException, IllegalArgumentException {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        if (m.find()) {
            throw new IllegalArgumentException("No special characters allowed!!!");
        } else if (playerRepository.findByUsername(username) == null) {
            inventoryRepository.save(new InventoryEntity(username));
            playerRepository.save(new PlayerEntity(username, password));
        } else {
            throw new AuthenticationException("Username already exists!!!");
        }
    }
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
            createUser(username, password);
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
        } catch (IllegalArgumentException e) {
            return JsonConvertor.convert(SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message("No special characters allowed")
                    .build());
        }
    }

}
