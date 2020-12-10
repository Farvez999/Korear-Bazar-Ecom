package com.frz.korearbazar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frz.korearbazar.Interface.CartInterface;
import com.frz.korearbazar.Interface.ProdInterface;
import com.frz.korearbazar.R;
import com.frz.korearbazar.activity.CartActivity;
import com.frz.korearbazar.model.CartModel;
import com.frz.korearbazar.model.ProdModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.frz.korearbazar.ApiInterface.JSONURL;
import static com.frz.korearbazar.ApiInterface.PDetailsImgUrl;
import static com.frz.korearbazar.ApiInterface.ProdImgUrl;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<CartModel> cartModelArrayList;
    Context context;
    CartInterface cartInterface;

    public CartAdapter(Context ctx, List<CartModel> dataModelArrayList){
        this.context=ctx;
        inflater = LayoutInflater.from(ctx);
        this.cartModelArrayList = dataModelArrayList;
        this.cartInterface=cartInterface;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_cart, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //http://ecom.hrventure.xyz/assets/images/thumbnails/
        Picasso.get().load(JSONURL+PDetailsImgUrl+cartModelArrayList.get(position).getImage()).into(holder.iv);
        holder.name.setText(cartModelArrayList.get(position).getTitle());
        holder.quantity.setText(cartModelArrayList.get(position).getQuantity());
        holder.price.setText(cartModelArrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView price;
        TextView quantity;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt_title);
            quantity = (TextView) itemView.findViewById(R.id.txtcount);
            price = (TextView) itemView.findViewById(R.id.txt_price);
            iv = (ImageView) itemView.findViewById(R.id.img_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int postion = this.getPosition();
//            ProdModel pm=cartModelArrayList.get(postion);
//            Toast.makeText(context, "postion  "+pm.getName(), Toast.LENGTH_SHORT).show();
//            cartInterface.setCart(pm);
        }
    }
}
