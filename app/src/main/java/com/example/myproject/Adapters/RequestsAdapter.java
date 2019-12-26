package com.example.myproject.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;
import com.example.myproject.models.Request;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {
    private ArrayList<Request> requests;
    private OnRequestClick onRequestClick;

    OnBtnClick onBtnClick;

    public void setOnBtnClick(OnBtnClick onBtnClick) {
        this.onBtnClick = onBtnClick;
    }

    public void setOnRequestClick(OnRequestClick onRequestClick) {
        this.onRequestClick = onRequestClick;
    }

    public RequestsAdapter(ArrayList<Request> requests) {
        this.requests = requests;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvJobTitle.setText(requests.get(position).getName());
        if (requests.get(position).getDate() != null) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(requests.get(position).getDate());
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            holder.tvDate.setText(year + " : " + month + " : " + day);
        }

        holder.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClick.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvDate;
        Button btnFinish;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnFinish = itemView.findViewById(R.id.btnFinish);

        }

    }

    public interface OnRequestClick {
        void onBtnFinishClicked(int position);
    }

    public interface OnBtnClick{
        void onClick(int i);
    }
}
