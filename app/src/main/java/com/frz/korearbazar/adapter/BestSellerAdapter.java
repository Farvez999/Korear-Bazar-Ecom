package com.frz.korearbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.frz.korearbazar.Interface.BestSeller;
import com.frz.korearbazar.MainActivity;
import com.frz.korearbazar.R;
import com.frz.korearbazar.activity.ItemDetailsActivity;
import com.frz.korearbazar.model.BestSellerModel;
import com.frz.korearbazar.model.ProdModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.frz.korearbazar.ApiInterface.JSONURL;
import static com.frz.korearbazar.ApiInterface.ProdImgUrl;


public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<BestSellerModel> bestModelArrayList;
    Context context;
    BestSeller bestSeller;
    public BestSellerAdapter(Context ctx, ArrayList<BestSellerModel> dataModelArrayList){
        this.context=ctx;
        inflater = LayoutInflater.from(ctx);
        this.bestModelArrayList = dataModelArrayList;
        this.bestSeller = bestSeller;
    }


    @Override
    public BestSellerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.product_item, parent, false);
        BestSellerAdapter.MyViewHolder holder = new BestSellerAdapter.MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BestSellerAdapter.MyViewHolder holder, final int position) {
        //http://ecom.hrventure.xyz/assets/images/thumbnails/
        Picasso.get().load(JSONURL+ProdImgUrl+bestModelArrayList.get(position).getThumbnail()).into(holder.iv);
        holder.name.setText(bestModelArrayList.get(position).getName());
        holder.price.setText(bestModelArrayList.get(position).getPrice());
        holder.previous_price.setText(bestModelArrayList.get(position).getPrevious_price());

    }


    @Override
    public int getItemCount() {
        return bestModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView price;
        TextView previous_price;
        TextView txt_desc;
        ImageView iv;
        TextView slug;



        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.proTitle);
            price = (TextView) itemView.findViewById(R.id.pro_price);
            previous_price = (TextView) itemView.findViewById(R.id.pro_priceoofer);
            iv = (ImageView) itemView.findViewById(R.id.pro_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int postion = this.getPosition();
            BestSellerModel pm=bestModelArrayList.get(postion);
            Toast.makeText(context, "postion  "+pm.getName(), Toast.LENGTH_SHORT).show();
            bestSeller.setBestSeller(pm);
        }
    }

}

