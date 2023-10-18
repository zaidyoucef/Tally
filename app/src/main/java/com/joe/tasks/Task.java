package com.joe.tasks;
public class Task {
    private int idTask;
    private  String textTask;
    private  int isCompleted;
    public Task(int idTask, String textTask, int isCompleted) {
        this.idTask = idTask;
        this.textTask = textTask;
        this.isCompleted = isCompleted;
    }
    public int getIdTask() {
        return idTask;
    }
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
    public String getTextTask() {
        return textTask;
    }
    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }
    public int getIsCompleted() {
        return isCompleted;
    }
    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}