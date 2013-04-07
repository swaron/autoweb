package com.hnair.monitor.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnair.monitor.domain.monitor.Task;
import com.hnair.monitor.service.MonitorService;

@Controller
@RequestMapping("/")
public class UrlMonitorController extends AbstractController {

    @Autowired
    MonitorService monitorService;

    @Autowired
    TaskModelValidator validator;
    
    @RequestMapping("/urlMonitor.do")
    public String index(Model model) {
        Task task = monitorService.getTaskById("0");
        if(task == null){
            task = monitorService.createOneBlankTask();
        }
        TaskModel taskModel = new TaskModel();
        taskModel.setId("0");
        taskModel.setConfig(task.getTaskConfig());
        taskModel.setLastError(task.getLastError());
        taskModel.setRunning(task.isRunning());
        taskModel.setLastResponse(task.getLastResponse() );
        model.addAttribute("taskJson", objectToJson(taskModel));
        model.addAttribute("task", taskModel);
        return "/urlMonitor";
    }

    @RequestMapping("/urlmonitor/saveTask.json")
    public void saveTask(TaskModel taskModel, Errors errors, Model model) {
        String id = taskModel.getId();
        Task task = monitorService.getTaskById(id);
        if(task == null){
//            model.addAttribute("errors", "task not found, unable to save.");
            task = monitorService.createOneBlankTask();
        }
        validator.validate(taskModel, errors);
        if(errors.hasErrors()){
            model.addAttribute("errors", "the inputs are not valid.");
            return ;
        }
        task.destroy();
        task.setTaskConfig(taskModel.getConfig());
        boolean saveResult = monitorService.storeTasksToFile();
        if(!saveResult){
            model.addAttribute("errors", "unable to save task to config file.");
            return ;
        }
    }
    @RequestMapping("/urlmonitor/taskAction.json")
    public void taskAction(String action, Model model) {
        Task task = monitorService.getTaskById("0");
        if("start".equals(action)){
            boolean startResult = task.start();
            if(!startResult){
                model.addAttribute("errors", "failed to start task. refer to server log for information.");
            }
        }else if("stop".equals(action)){
            boolean stopResult = task.stop();
            if(!stopResult){
                model.addAttribute("errors", "failed to stop task. refer to server log for information.");
            }
        }else{
            model.addAttribute("errors", "unsupport action type, only start and stop is supported.");
        }
    }
}
