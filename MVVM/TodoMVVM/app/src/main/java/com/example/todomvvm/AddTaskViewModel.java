package com.example.todomvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.Todo;

public class AddTaskViewModel extends ViewModel {

    private LiveData<Todo> todo;

    public AddTaskViewModel(AppDatabase database, int todoId){
        todo = database.todoDao().loadTodoById(todoId);
    }

    public LiveData<Todo> getTodo() {
        return todo;
    }


}
