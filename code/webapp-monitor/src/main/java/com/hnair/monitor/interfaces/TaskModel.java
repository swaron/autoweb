package com.hnair.monitor.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hnair.monitor.domain.monitor.TaskConfig;

public class TaskModel {
    public static class ServerError {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String htmlResponse;
        String occurTime;

        public String getHtmlResponse() {
            return htmlResponse;
        }

        public void setHtmlResponse(String htmlResponse) {
            this.htmlResponse = htmlResponse;
        }

        public String getOccurTime() {
            return occurTime;
        }

        public void setOccurTime(Date occurTime) {
            this.occurTime = dateFormat.format(occurTime);
        }


    }
    String id;
    TaskConfig config;
    ServerError lastError;
    String lastResponse;
    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }
    boolean running;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskConfig getConfig() {
        return config;
    }

    public void setConfig(TaskConfig config) {
        this.config = config;
    }

    public ServerError getLastError() {
        return lastError;
    }

    public void setLastError(ServerError lastError) {
        this.lastError = lastError;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
