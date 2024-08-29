package bg.softuni.finebeard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinebeardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinebeardApplication.class, args);
	}

}
