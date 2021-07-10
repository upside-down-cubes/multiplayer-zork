package upsidedowncubes.multiplayerzork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import upsidedowncubes.multiplayerzork.database.Player;
import upsidedowncubes.multiplayerzork.database.PlayerRepository;

@SpringBootApplication
public class MultiplayerZorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiplayerZorkApplication.class, args);
	}
}
