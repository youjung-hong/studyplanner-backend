package com.chanel.toy.nemo;

import com.chanel.toy.nemo.repository.TodoItemActionLogRepository;
import com.chanel.toy.nemo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NemoApplication {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoItemActionLogRepository todoItemActionLogRepository;

    public static void main(String[] args) {
        SpringApplication.run(NemoApplication.class, args);
    }

}
