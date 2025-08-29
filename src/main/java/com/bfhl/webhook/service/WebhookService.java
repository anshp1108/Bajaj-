package com.bfhl.webhook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bfhl.webhook.model.WebhookRequest;
import com.bfhl.webhook.model.WebhookResponse;
import com.bfhl.webhook.model.SubmissionRequest;

@Service
public class WebhookService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    public void executeWebhookFlow() throws Exception {
        System.out.println("Step 1: Generating webhook...");
        WebhookResponse webhookResponse = generateWebhook();

        System.out.println("Received webhook response: " + webhookResponse);

        System.out.println("Step 2: Solving SQL problem...");
        String sqlQuery = solveSqlProblem();

        System.out.println("SQL Query generated: " + sqlQuery);

        System.out.println("Step 3: Submitting solution...");
        submitSolution(webhookResponse.getAccessToken(), sqlQuery);

        System.out.println("Solution submitted successfully!");
    }

    private WebhookResponse generateWebhook() throws Exception {
        WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                GENERATE_WEBHOOK_URL, entity, WebhookResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new Exception("Failed to generate webhook: " + response.getStatusCode());
        }
    }

    private String solveSqlProblem() {
        return "SELECT p.AMOUNT as SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME, "
             + "FLOOR(DATEDIFF(CURDATE(), e.DOB) / 365.25) as AGE, d.DEPARTMENT_NAME "
             + "FROM PAYMENTS p "
             + "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID "
             + "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID "
             + "WHERE DAY(p.PAYMENT_TIME) != 1 "
             + "ORDER BY p.AMOUNT DESC "
             + "LIMIT 1;";
    }

    private void submitSolution(String accessToken, String sqlQuery) throws Exception {
        SubmissionRequest request = new SubmissionRequest(sqlQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<SubmissionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                SUBMIT_WEBHOOK_URL, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Submission response: " + response.getBody());
        } else {
            throw new Exception("Failed to submit solution: " + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
