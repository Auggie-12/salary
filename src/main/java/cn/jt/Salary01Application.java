package cn.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"cn.jt.mapper"})
//@ComponentScan(basePackages = {"cn.jt.service", "cn.jt.service.impl","cn.jt.mapper"})
public class Salary01Application {

    public static void main(String[] args) {
        SpringApplication.run(Salary01Application.class, args);
    }

}
