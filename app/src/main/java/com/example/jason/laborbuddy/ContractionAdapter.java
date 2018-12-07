package com.example.jason.laborbuddy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContractionAdapter extends RecyclerView.Adapter<ContractionAdapter.MyViewHolder> {

    private List<Contraction> contractionList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView startTime;
        public TextView duration;
        public TextView frequency;

        public MyViewHolder(View view) {

            super(view);
            startTime = (TextView) view.findViewById(R.id.startTime);
            duration = (TextView) view.findViewById(R.id.duration);
            frequency = (TextView) view.findViewById(R.id.frequency);

        }

    }

    public ContractionAdapter(List<Contraction> contractionList) {

        this.contractionList = contractionList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contraction_list_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Contraction contraction = contractionList.get(position);
        holder.startTime.setText(contraction.getStartTime());
        holder.duration.setText(contraction.getDuration());
        holder.frequency.setText(contraction.getFrequency());

    }

    @Override
    public int getItemCount() {

        return contractionList.size();

    }
}
