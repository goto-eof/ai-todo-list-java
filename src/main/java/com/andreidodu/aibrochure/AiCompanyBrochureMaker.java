package com.andreidodu.aibrochure;

import com.andreidodu.aibrochure.service.BrochureMakerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiCompanyBrochureMaker {

    public static void main(String[] args) {
        var context = SpringApplication.run(AiCompanyBrochureMaker.class, args);
        System.out.println("Brochure MAker loading. Please wait...");
        ((BrochureMakerService) context.getBean("brochureMakerServiceImpl")).makeBrochure("https://ubuntu.com/");
    }

}