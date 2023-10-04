package com.rehman.eorderingsystem.Aadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.Model.OrderModel;
import com.rehman.eorderingsystem.R;
import com.rehman.eorderingsystem.User.CartActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{
    private Context context;
    private List<OrderModel> mDatalist;

    public OrderAdapter(Context context, List<OrderModel> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);

        return new MyViewHolder(myview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {


        OrderModel model = mDatalist.get(position);

        holder.tv_foodName.setText("Namee: " + model.getName());
        holder.tv_phone.setText("Phone: " + model.getPhone());
        holder.tv_date.setText("Date: " + model.getCurrentDate());


    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_foodName,tv_phone,tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_date = itemView.findViewById(R.id.tv_date);


        }
    }
}
