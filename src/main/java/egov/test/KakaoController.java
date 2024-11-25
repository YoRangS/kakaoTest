package egov.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class KakaoController {
//	@RequestMapping(value = "/hello.do")
//	public String hello() {
//		return "hello";
//	}

	@GetMapping("/{name}")
	public String testFunction(@PathVariable String name) {
		String returnValue = "test return value RESTAPI: " + name;
		
		return returnValue;
	}
}