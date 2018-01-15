package sample

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@CompileStatic
@SpringBootApplication
class ExampleBulletinsApplication {

    static void main(String[] args) {
        SpringApplication.run ExampleBulletinsApplication, args
    }
}
