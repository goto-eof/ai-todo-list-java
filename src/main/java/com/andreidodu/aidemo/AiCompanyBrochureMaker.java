package com.andreidodu.aidemo;

import com.andreidodu.aidemo.agent.BrochureMakerAgentImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiCompanyBrochureMaker {

    public static void main(String[] args) {
        var context = SpringApplication.run(AiCompanyBrochureMaker.class, args);
        System.out.println("Brochure MAker loading. Please wait...");
        ((BrochureMakerAgentImpl) context.getBean("brochureMakerAgentImpl")).makeBrochure("https://ubuntu.com/");
    }

}