package gcp.hello.world.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World From Gcp!";
    }

    @GetMapping("/say")
    public String saySomething() {
        return "Nice to meet you";
    }
}
