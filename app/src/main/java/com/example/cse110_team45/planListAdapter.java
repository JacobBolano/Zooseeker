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

    private List<planListItem> planItems = Collections.emptyList();
    private Consumer<planListItem> onCheckBoxClicked;
    private BiConsumer<planListItem, String> onTextEditedHandler;
    private Consumer<planListItem> onDeleteClicked;

    public void setTodoListItems(List<planListItem> newTodoItems) {
        this.planItems.clear();
        this.planItems = newTodoItems;
        notifyDataSetChanged();
    }

    public void setOnCheckBoxClickedHandler(Consumer<planListItem> onCheckBoxClicked) {
        this.onCheckBoxClicked = onCheckBoxClicked;
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
    }

    @Override
    public int getItemCount() {
        return planItems.size();
    }

    @Override
    public long getItemId(int position) {
        return planItems.get(position).id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private planListItem planItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.plan_item_text);

            this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                if (!hasFocus) {
                    onTextEditedHandler.accept(planItem, textView.getText().toString());
                }
            });
        }

        public planListItem getPlanItem() {return planItem;}

        public void setTodoItem(planListItem todoItem) {
            this.planItem = todoItem;
            this.textView.setText(todoItem.text);
        }
    }

}
