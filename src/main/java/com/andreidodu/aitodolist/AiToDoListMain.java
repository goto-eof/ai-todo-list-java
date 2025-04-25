package com.andreidodu.aitodolist;

import com.andreidodu.aitodolist.service.ToDoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class AiToDoListMain implements CommandLineRunner {

    private final ApplicationContext context;
    private static final Logger LOG = LoggerFactory.getLogger(AiToDoListMain.class);

    public AiToDoListMain(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(AiToDoListMain.class, args);
    }

    @Override
    public void run(String... args) {
        ToDoListService toDoListService = context.getBean(ToDoListService.class);

        LOG.info("Application started :)");
        LOG.info("Start to write something to the ToDo list manager AI:");
        String message = null;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your message:");
            message = scanner.nextLine();
            toDoListService.processMessage(message);
        } while (!"exit".equalsIgnoreCase(message));
        System.out.println("You entered string " + message);
    }

}