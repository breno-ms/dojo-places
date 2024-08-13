package br.com.alura.dojoplaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class DojoPlacesApplication {

    @GetMapping("/oi")
    public String oi() {
        return "/oi";
    }

    public static void main(String[] args) {
        SpringApplication.run(DojoPlacesApplication.class, args);
    }

}
