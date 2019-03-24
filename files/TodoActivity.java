package com.example.todoapplication;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {

    private String[] mTodos;
    private int mTodoIndex = 0;


    public static final String TAG = TodoActivity.class.getSimpleName();

    private static final String TODO_INDEX = "com.example.todoIndex";

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
        Log.d(TAG, "onSaveInstanceState(): screen-rotated - activity going to be destroyed");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
            Log.d(TAG, "onCreate(): activity re-created");

        } else {
            // Activity is being created anew. No prior saved
            // instance state information available in Bundle object.
            Log.d(TAG, "onCreate(): activity created anew");
        }


        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todos);

        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);
        textViewTodo.setText(mTodos[mTodoIndex]);

        Button buttonNext = (Button) findViewById(R.id.buttonNext);

        Button buttonPrev = (Button) findViewById(R.id.buttonPrev);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTodoIndex = (mTodoIndex + 1) % mTodos.length;
                textViewTodo.setText(mTodos[mTodoIndex]);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() - the activity is about to become visible");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,
                "onResume() - the activity has become visible (it is now " +
                        "\"resumed\")");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,
                "onPause() - another activity is taking focus (this activity " +
                        "is about to be \"paused\")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,
                "onStop() - the activity is no longer visible (it is now " +
                        "\"stopped\")");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() - the activity is about to be restarted()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() - the activity is about to be destroyed");
    }
}
