package com.example.myproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myproject.R;
import com.example.myproject.models.Job;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.MyViewHolder> {

    private ArrayList<Job> jobs;
    private OnJobClickListener onJobClickListener;


    public void setOnJobClickListener(OnJobClickListener onJobClickListener) {
        this.onJobClickListener = onJobClickListener;
    }

    public HomeRvAdapter(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public HomeRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvAdapter.MyViewHolder holder, int position) {
        holder.imgJob.setImageResource(jobs.get(position).getJobImgId());
        holder.tvJobNme.setText(jobs.get(position).getJobName());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgJob ;
        TextView tvJobNme;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgJob=itemView.findViewById(R.id.imgJob);
            tvJobNme=itemView.findViewById(R.id.tvJobNme);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onJobClickListener.onJobClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onJobClickListener.onJobLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface OnJobClickListener{
        void onJobClick(int position);
        void onJobLongClick(int position);
    }
}
