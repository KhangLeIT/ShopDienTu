package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangdientu.R;

import java.util.List;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.ViewHolder> {
    Context context;
    List<String> list;

    public ErrorAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Error;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Error = itemView.findViewById(R.id.item_error);
        }

        public void setData(int pos) {
            String txtError = list.get(pos);
            Error.setText(txtError);
        }
    }
}
