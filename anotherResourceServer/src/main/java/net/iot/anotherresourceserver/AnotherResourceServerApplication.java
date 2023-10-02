package net.iot.anotherresourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SpringBootApplication
public class AnotherResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotherResourceServerApplication.class, args);
	}

}

@Service
class HomeService {
	@PreAuthorize("hasAuthority('SCOPE_user.read')")
	public Map<String, String> greet() {
		var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Map.of("message", "Home " + jwt.getSubject());
	}
}

@Controller
@ResponseBody
class HomeController {

	private final HomeService homeService;

	HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@GetMapping("/home")
	public Map<String, String> home() {
		return homeService.greet();
	}

	@GetMapping("/home/yo")
	public String yobro() {
		return "Hi there!";
	}
}


