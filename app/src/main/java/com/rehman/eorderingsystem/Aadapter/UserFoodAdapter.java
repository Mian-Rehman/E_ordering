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
import com.rehman.eorderingsystem.R;
import com.rehman.eorderingsystem.User.CartActivity;

import java.util.List;

public class UserFoodAdapter extends RecyclerView.Adapter<UserFoodAdapter.MyViewHolder>
{
    private Context context;
    private List<FoodModel> mDatalist;

    public UserFoodAdapter(Context context, List<FoodModel> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public UserFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.food_list_layout,parent,false);

        return new MyViewHolder(myview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserFoodAdapter.MyViewHolder holder, int position) {

        FoodModel model = mDatalist.get(position);

        holder.tv_foodName.setText(model.getFoodName());
        holder.tv_foodDes.setText(model.getFoodDescription());
        holder.tv_foodPrice.setText("from RS: " + model.getFoodPrice());

        Glide.with(context).load(model.getFoodImage()).into(holder.food_image);

        holder.delete_image.setVisibility(View.GONE);
        holder.edit_image.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CartActivity.class);
            intent.putExtra("foodName",model.getFoodName());
            intent.putExtra("foodDescription",model.getFoodDescription());
            intent.putExtra("foodPrice",model.getFoodPrice());
            intent.putExtra("foodCategory",model.getFoodCategory());
            intent.putExtra("foodImage",model.getFoodImage());
            intent.putExtra("foodQuantity",model.getFoodQuantity());
            intent.putExtra("foodKey",model.getFoodKey());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView food_image,delete_image,edit_image;
        TextView tv_foodName,tv_foodDes,tv_foodPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            food_image = itemView.findViewById(R.id.food_image);
            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_foodDes = itemView.findViewById(R.id.tv_foodDes);
            tv_foodPrice = itemView.findViewById(R.id.tv_foodPrice);
            delete_image = itemView.findViewById(R.id.delete_image);
            edit_image = itemView.findViewById(R.id.edit_image);

        }
    }
}
