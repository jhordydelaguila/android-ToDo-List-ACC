package com.example.delaguila.todolistaac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.delaguila.todolistaac.database.AppDatabase;
import com.example.delaguila.todolistaac.database.TaskEntry;

public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
