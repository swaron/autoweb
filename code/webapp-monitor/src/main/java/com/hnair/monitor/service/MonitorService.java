package com.hnair.monitor.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.hnair.monitor.common.email.EmailService;
import com.hnair.monitor.domain.monitor.Task;
import com.hnair.monitor.domain.monitor.TaskConfig;
import com.thoughtworks.xstream.XStream;

@Service
public class MonitorService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EmailService emailService;
    private List<Task> tasks = new ArrayList<Task>();
    @Value("file:${user.home}/webapp-monitor/config.xml")
    private Resource configFile;
    XStream xStream = new XStream();

    @PostConstruct
    public void init() {
        loadTasksFromFile();
    }
    
    @PreDestroy
    public void close() {
        for (Task task : tasks) {
            task.stop();
        }
    }
    
    public Task getTaskById(String id) {
        try {
            return tasks.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    private void loadTasksFromFile() {
        removeAllTask();
        try {
            if(configFile.exists()){
                @SuppressWarnings("unchecked")
                List<TaskConfig> list = (List<TaskConfig>) xStream.fromXML(configFile.getInputStream());
                for (TaskConfig taskConfig : list) {
                    Task task = new Task(emailService);
                    task.init(taskConfig);
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            logger.warn("failed to read config from file.", e);
        }
    }
    public synchronized boolean storeTasksToFile() {
        List<TaskConfig> list = new ArrayList<TaskConfig>();
        for (Task task : tasks) {
            list.add(task.toConfig());
        }
        String xml = xStream.toXML(list);
        try {
            FileUtils.writeStringToFile(configFile.getFile(), xml);
        } catch (Exception e) {
            logger.error("failed to store config to file.", e);
            return false;
        }
        return true;
        
    }
    public synchronized void removeAllTask() {
        for (Task task : tasks) {
            task.destroy();
        }
        tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }


    public Task createOneBlankTask() {
        Task task = new Task(emailService);
        task.getTaskConfig().setName("Task");
        this.tasks.add(task);
        return task;
    }
}
