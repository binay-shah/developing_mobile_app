package com.example.todomvvm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todomvvm.database.Todo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<Todo> mTodoList;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TodoAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public TodoAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.todo_layout, parent, false);

        return new TodoViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        // Determine the values of the wanted data
        Todo taskEntry = mTodoList.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if (mTodoList == null) {
            return 0;
        }
        return mTodoList.size();
    }


    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTodoList(List<Todo> todos) {
        mTodoList = todos;
        notifyDataSetChanged();
    }

    public List<Todo> getTodos(){
        return mTodoList;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    // Inner class for creating ViewHolders
    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TodoViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mTodoList.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
