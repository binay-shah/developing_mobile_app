package com.example.todoappfragment.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.todoappfragment.R;
import com.example.todoappfragment.fragments.TodoListFragment;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            TodoListFragment todoListFragment = new TodoListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, todoListFragment)
                    .commit();
        }
    }
}
