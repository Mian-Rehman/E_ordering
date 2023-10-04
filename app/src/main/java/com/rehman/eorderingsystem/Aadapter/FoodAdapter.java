package com.rehman.eorderingsystem.Aadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.R;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodModel,FoodAdapter.MyViewHolderTeacher>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;

    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolderTeacher holder, @SuppressLint("RecyclerView") int position, @NonNull FoodModel model) {


        holder.tv_foodName.setText(model.getFoodName());
        holder.tv_foodDes.setText(model.getFoodDescription());
        holder.tv_foodPrice.setText("from RS: " + model.getFoodPrice());

        holder.edit_image.setVisibility(View.GONE);

        Glide.with(context).load(model.getFoodImage()).into(holder.food_image);

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tv_foodDes.getContext());
                builder.setTitle("Delete Parlor profile");
                builder.setMessage("Press Yes or No?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("KitchenFood")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolderTeacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_layout,parent,false);
        return new MyViewHolderTeacher(view);
    }




    public class MyViewHolderTeacher extends RecyclerView.ViewHolder {

        ImageView food_image,edit_image,delete_image;
        TextView tv_foodName,tv_foodDes,tv_foodPrice;


        public MyViewHolderTeacher(@NonNull View itemView) {
            super(itemView);

            food_image = itemView.findViewById(R.id.food_image);
            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_foodDes = itemView.findViewById(R.id.tv_foodDes);
            tv_foodPrice = itemView.findViewById(R.id.tv_foodPrice);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);

        }
    }
}
