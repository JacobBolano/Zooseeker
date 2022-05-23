package com.example.cse110_team45;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class planListAdapter extends RecyclerView.Adapter<planListAdapter.ViewHolder> {

    private List<String> planItems = Collections.emptyList();

    public void setTodoListItems(List<String> newTodoItems) {
        this.planItems.clear();
        this.planItems = newTodoItems;
        notifyDataSetChanged();
    }

    public List<String> getPlanItems(){
        return planItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.plan_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTodoItem(planItems.get(position));
        holder.textView.setBackgroundColor(0xFF808080);
    }

    @Override
    public int getItemCount() {
        return planItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private String planItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.plan_item_text);
        }

        public String getPlanItem() {return planItem;}

        public void setTodoItem(String todoItem) {
            this.planItem = todoItem;
            this.textView.setText(todoItem);
        }
    }

}
