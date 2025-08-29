package com.bfhl.webhook.model;

public class SubmissionRequest {
    private String finalQuery;

    public SubmissionRequest() {}

    public SubmissionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }

    @Override
    public String toString() {
        return "SubmissionRequest{" +
                "finalQuery='" + finalQuery + '\'' +
                '}';
    }
}
