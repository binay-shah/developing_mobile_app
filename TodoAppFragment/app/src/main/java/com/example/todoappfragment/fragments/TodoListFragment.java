package com.example.todoappfragment.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoappfragment.R;
import com.example.todoappfragment.model.Todo;
import com.example.todoappfragment.model.TodoDS;
import com.example.todoappfragment.activities.TodoActivity;
import com.example.todoappfragment.activities.TodoPagerActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoListFragment extends Fragment {

    private RecyclerView mTodoRecyclerView;
    TodoAdapter mTodoAdapter;

    public TodoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()) );

        updateUI();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){

        //ArrayList todos = new ArrayList<>();
        TodoDS todoModel = TodoDS.getInstance(getContext());
        List<Todo> todoList = todoModel.getTodos();

        if (mTodoAdapter == null) {
            mTodoAdapter = new TodoAdapter(todoList);
            mTodoRecyclerView.setAdapter(mTodoAdapter);
        } else {
            mTodoAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_todo:

                Todo todo = new Todo();
                TodoDS.getInstance(getActivity()).addTodo(todo);

                Intent intent = TodoActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class TodoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Todo mTodo;
        private TextView mTextViewTitle;
        private TextView mTextViewDate;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_todo, parent, false));

            itemView.setOnClickListener(this);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.todo_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.todo_date);

        }

        @Override
        public void onClick(View view) {
            // have a Toast for now
            Toast.makeText(
                    getActivity(),
                    mTodo.getTitle() + " clicked",
                    Toast.LENGTH_SHORT)
                    .show();

            Intent intent = TodoPagerActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);

        }

        public void bind(Todo todo){
            mTodo = todo;
            mTextViewTitle.setText(mTodo.getTitle());
            mTextViewDate.setText(mTodo.getDate().toString());
        }

    }

    public class TodoAdapter extends RecyclerView.Adapter<TodoListFragment.TodoHolder> {

        private List<Todo> mTodos;
        Context context;

        public TodoAdapter(List<Todo> todos) {
            mTodos = todos;
            this.context = context;
        }

        @NonNull
        @Override
        public TodoListFragment.TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TodoListFragment.TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListFragment.TodoHolder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }
    }

}
