package com.example.michelleclarisse.samplerep.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michelleclarisse.samplerep.Interface.ItemClickListener;
import com.example.michelleclarisse.samplerep.R;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView productName;
    public ImageView productImage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
        productName = (TextView)itemView.findViewById(R.id.product_name);
        productImage = (ImageView)itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(), false);
    }
}
