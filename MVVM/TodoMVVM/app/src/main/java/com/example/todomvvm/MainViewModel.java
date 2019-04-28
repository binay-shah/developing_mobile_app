package com.example.todomvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.Todo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> mTodos;
    TodoRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodos = mRepository.getAllWords();
    }

    public LiveData<List<Todo>> getTodos(){
        return mTodos;
    }

    public void insert(Todo todo) { mRepository.insert(todo); }

    public void delete(Todo todo) { mRepository.delete(todo); }

    public void update(Todo todo) { mRepository.update(todo); }
}
