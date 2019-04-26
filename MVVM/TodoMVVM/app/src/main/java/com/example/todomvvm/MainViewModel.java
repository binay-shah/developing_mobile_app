package com.example.todomvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.Todo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> todos;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        todos = database.todoDao().getAllTodos();
    }

    public LiveData<List<Todo>> getTodos(){
        return todos;
    }
}
