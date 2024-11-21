package egov.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KakaoController {
	@RequestMapping(value = "/hello.do")
	public String hello() {
		return "hello";
	}
}