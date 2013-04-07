package com.hnair.monitor.domain.monitor;

public class TaskConfig {
    String name;
    String url;
    String expectedContent;
    String subscribers;
    boolean stopOnError = true;
    Long probeInterval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(String subscribers) {
        this.subscribers = subscribers;
    }

    public Long getProbeInterval() {
        return probeInterval;
    }

    public void setProbeInterval(Long probeInterval) {
        this.probeInterval = probeInterval;
    }


    public String getExpectedContent() {
        return expectedContent;
    }

    public void setExpectedContent(String expectedContent) {
        this.expectedContent = expectedContent;
    }

    public boolean isStopOnError() {
        return stopOnError;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }
}
