package com.frz.korearbazar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.frz.korearbazar.R;
import com.frz.korearbazar.model.CateModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CateAdapter extends RecyclerView.Adapter<CateAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CateModel> dataModelArrayList;

    public CateAdapter(Context ctx, ArrayList<CateModel> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.category_item, parent, false);
        CateAdapter.MyViewHolder holder = new CateAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /// http://ecom.hrventure.xyz/assets/images/categories/1605095240leg-of-lamb-500x500.png
        Picasso.get().load("http://ecom.hrventure.xyz/assets/images/categories/"+dataModelArrayList.get(position).getPhoto()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.cate_title);
            iv = (ImageView) itemView.findViewById(R.id.cate_imageView);
        }

    }
}

