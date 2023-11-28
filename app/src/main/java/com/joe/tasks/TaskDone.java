package com.joe.tasks;
public class TaskDone {
    private final int idTask;
    private final String textTask;
    private final String completeDate;

    public TaskDone(int idTask, String textTask, int isCompleted, String completeDate) {
        this.idTask = idTask;
        this.textTask = textTask;
        this.completeDate = completeDate;
    }
    public int getIdTask() {
        return idTask;
    }

    public String getTextTask() {
        return textTask;
    }

    public String getCompleteDate() {
        return completeDate;
    }
}

