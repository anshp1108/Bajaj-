package com.bfhl.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bfhl.webhook.service.WebhookService;

@SpringBootApplication
public class BfhlWebhookApplication implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    public static void main(String[] args) {
        SpringApplication.run(BfhlWebhookApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting BFHL Webhook Application...");
        try {
            webhookService.executeWebhookFlow();
            System.out.println("Webhook flow completed successfully!");
        } catch (Exception e) {
            System.err.println("Error executing webhook flow: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
