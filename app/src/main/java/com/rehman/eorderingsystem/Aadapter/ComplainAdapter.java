package com.rehman.eorderingsystem.Aadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rehman.eorderingsystem.Model.ComplainModel;
import com.rehman.eorderingsystem.Model.OrderModel;
import com.rehman.eorderingsystem.R;

import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.MyViewHolder>
{
    private Context context;
    private List<ComplainModel> mDatalist;

    public ComplainAdapter(Context context, List<ComplainModel> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public ComplainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.complain_layout,parent,false);

        return new MyViewHolder(myview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComplainAdapter.MyViewHolder holder, int position) {


        ComplainModel model = mDatalist.get(position);

        holder.tv_message.setText(model.getMessage());


    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_message = itemView.findViewById(R.id.tv_message);



        }
    }
}
