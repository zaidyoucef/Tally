package com.joe.tasks;
public class Task {
    private final int idTask;
    private final String textTask;
    private final int isCompleted;
    public Task(int idTask, String textTask, int isCompleted) {
        this.idTask = idTask;
        this.textTask = textTask;
        this.isCompleted = isCompleted;
    }
    public int getIdTask() {
        return idTask;
    }

    public String getTextTask() {
        return textTask;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

}