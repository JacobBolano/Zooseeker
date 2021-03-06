package com.example.cse110_team45;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.ViewHolder>{
    private List<MockIndividualEdge> individualDirectionList = Collections.emptyList();

    public void setIndividualDirectionListItems(List<MockIndividualEdge> newDirections) {
        this.individualDirectionList.clear();
        this.individualDirectionList = newDirections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_directions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIndividualDirection(individualDirectionList.get(position));
    }

    @Override
    public int getItemCount() {
        return individualDirectionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private MockIndividualDirection individualDirection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.individualDirectionDetail);
        }

        public MockIndividualDirection getIndividualDirection(){return individualDirection;}

        public void setIndividualDirection(MockIndividualDirection individualDirection){
            this.individualDirection = individualDirection;
            this.textView.setText(individualDirection.getDetails());
        }
    }
}
