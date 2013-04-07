package com.hnair.monitor.domain.monitor;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnair.monitor.common.email.EmailContent;
import com.hnair.monitor.common.email.EmailService;
import com.hnair.monitor.interfaces.TaskModel.ServerError;

public class Task implements Runnable{
    EmailService emailService;
    Logger logger = LoggerFactory.getLogger(getClass());
    HttpClient httpclient = new DefaultHttpClient();
    private ScheduledExecutorService scheduler;
    private TaskConfig taskConfig = new TaskConfig();

    ServerError lastError;
    String lastResponse;
    boolean running = false;

    public Task(EmailService emailService) {
        this.emailService = emailService;
    }

    public ServerError getLastError() {
        return lastError;
    }

    public void setLastError(ServerError lastError) {
        this.lastError = lastError;
    }

    public String getLastResponse() {
        return lastResponse;
    }
    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }
    public void destroy() {
        this.stop();
    }

    public void init(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    public TaskConfig getTaskConfig() {
        return taskConfig;
    }

    public void setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    public TaskConfig toConfig() {
        return taskConfig;
    }

    public synchronized boolean start() {
        if(running){
            return true;
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this, 1, taskConfig.probeInterval, TimeUnit.SECONDS);
        lastError = null;
        running = true;
        return true;
    }

    public synchronized boolean stop() {
        if (!running) {
            return true;
        }
        scheduler.shutdownNow();
        running = false;
        return true;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        if(getTaskConfig().isStopOnError() && lastError != null){
            return;
        }
        HttpGet httpget = new HttpGet(taskConfig.getUrl());
        logger.debug("get url: " + taskConfig.getUrl());
        ServerError serverError = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            lastResponse = StringUtils.substring(content, 0, 10240) ;
            if(code != 200){
                serverError = new ServerError();
                serverError.setOccurTime(new Date());
                serverError.setHtmlResponse(content);
            }else if(StringUtils.isNotBlank(getTaskConfig().getExpectedContent())){
                if(!content.contains(getTaskConfig().getExpectedContent())){
                    serverError = new ServerError();
                    serverError.setOccurTime(new Date());
                    serverError.setHtmlResponse(content);
                }
            }
        } catch (ClientProtocolException e) {
            logger.warn("Invalid ClientProtocol",e);
            serverError = new ServerError();
            serverError.setOccurTime(new Date());
            serverError.setHtmlResponse("Invalid ClientProtocol");
        } catch (IOException e) {
            logger.warn("connect problem",e);
            serverError = new ServerError();
            serverError.setOccurTime(new Date());
            serverError.setHtmlResponse("Connection problem.\n" + e.toString());
        }
        if(serverError != null){
            sentNotifyEmail(serverError);
            lastError = serverError;
        }
    }

    private void sentNotifyEmail(ServerError serverError) {
        EmailContent content = new EmailContent("Webapp Monitor Notification", serverError.getHtmlResponse());
        logger.debug("Sending ServerError notification");
        emailService.sendEmail(taskConfig.getSubscribers(), content);
    }
}
