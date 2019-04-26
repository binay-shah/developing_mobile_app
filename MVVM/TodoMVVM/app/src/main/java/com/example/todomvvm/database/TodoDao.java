package com.example.todomvvm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public  interface TodoDao {

    @Query("select * from todos order by priority")
    LiveData<List<Todo>> getAllTodos();

    @Insert
    void insertTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo todo);

    @Query("select * from todos where id = :id")
    LiveData<Todo> loadTodoById(int id);

}
