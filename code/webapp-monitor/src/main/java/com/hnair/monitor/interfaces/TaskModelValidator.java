package com.hnair.monitor.interfaces;

import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TaskModelValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(TaskModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskModel model = (TaskModel) target;
        model.getConfig().getUrl();
        try {
            URL url = new URL(model.getConfig().getUrl());
            String path = url.getPath();
            System.out.println(path);
        } catch (Exception e) {
            errors.rejectValue("url", "Malformed URL");
        }
        model.getConfig().getSubscribers();
    }
}
