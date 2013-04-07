package com.hnair.monitor.common.email;

public class EmailContent {
    private String subject;
    private String body;

    public EmailContent() {
        super();
    }

    public EmailContent(String subject, String body) {
        super();
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
