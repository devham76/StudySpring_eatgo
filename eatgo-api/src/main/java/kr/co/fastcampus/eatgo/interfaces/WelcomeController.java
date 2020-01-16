package kr.co.fastcampus.eatgo.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller 로 만들어주기 위해 해당 어노테이션을 붙인다
public class WelcomeController {
    @GetMapping("/")
    public String hello(){
        return "hello world^^!!";
    }
}
