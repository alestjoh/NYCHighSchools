package com.example.nychighschools.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nychighschools.R;
import com.example.nychighschools.model.SchoolData;

import java.util.List;

public class SchoolDataAdapter extends RecyclerView.Adapter<SchoolDataAdapter.SchoolDataViewHolder> {

    List<SchoolData> data;

    public SchoolDataAdapter(List<SchoolData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SchoolDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.recycler_item_layout,
                viewGroup, false);

        return new SchoolDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolDataViewHolder schoolDataViewHolder, int i) {
        schoolDataViewHolder.name.setText(data.get(i).school_name);
        schoolDataViewHolder.description.setText(data.get(i).overview_paragraph);

        schoolDataViewHolder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(view.getContext(), SatScoresActivity.class);
            intent.putExtra(SatScoresActivity.SCHOOL_NAME_EXTRA, data.get(i).dbn);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SchoolDataViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public CardView cardView;

        public SchoolDataViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_rItem);
            description = itemView.findViewById(R.id.tv_description_rItem);
            cardView = itemView.findViewById(R.id.card_rItem);
        }
    }
}
