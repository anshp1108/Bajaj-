package com.bfhl.webhook.model;

public class WebhookResponse {
    private String webhook;
    private String accessToken;

    public WebhookResponse() {}

    public String getWebhook() { return webhook; }
    public String getAccessToken() { return accessToken; }

    public void setWebhook(String webhook) { this.webhook = webhook; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    @Override
    public String toString() {
        return "WebhookResponse{" +
                "webhook='" + webhook + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
