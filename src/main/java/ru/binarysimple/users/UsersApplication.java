package ru.binarysimple.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(
//exclude = {
//DataSourceAutoConfiguration .class,
//HibernateJpaAutoConfiguration .class,
//LiquibaseAutoConfiguration .class
//    })
//http://localhost:8080/swagger-ui/index.html
public class UsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
