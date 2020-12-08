package com.frz.korearbazar.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frz.korearbazar.Interface.ProdInterface;
import com.frz.korearbazar.R;
import com.frz.korearbazar.activity.ItemDetailsActivity;
import com.frz.korearbazar.model.ProdModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.frz.korearbazar.ApiInterface.JSONURL;
import static com.frz.korearbazar.ApiInterface.ProdImgUrl;


public class ProdAdapter extends RecyclerView.Adapter<ProdAdapter.MyViewHolder> {

private LayoutInflater inflater;
private ArrayList<ProdModel> dataModelArrayList;
Context context;
ProdInterface prodInterface;
static String a_slag=null;
public ProdAdapter(Context ctx, ArrayList<ProdModel> dataModelArrayList,ProdInterface prodInterface){
        this.context=ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.prodInterface=prodInterface;
        }



@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.product_item, parent, false);
        ProdAdapter.MyViewHolder holder = new ProdAdapter.MyViewHolder(itemView);

        return holder;
        }

@Override
public void onBindViewHolder(final MyViewHolder holder, final int position) {
    //http://ecom.hrventure.xyz/assets/images/thumbnails/
        Picasso.get().load(JSONURL+ProdImgUrl+dataModelArrayList.get(position).getThumbnail()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.price.setText(dataModelArrayList.get(position).getPrice());
        holder.previous_price.setText(dataModelArrayList.get(position).getPrevious_price());
       //holder.txt_desc.setText(dataModelArrayList.get(position).getDetails());
//        a_slag=dataModelArrayList.get(position).getSlug();
//        holder.slug.setText(a_slag);
//
//        holder.slug.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ProdModel pm=dataModelArrayList.get(position);
////                Toast.makeText(context, "postion  "+pm.getName(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(context , ItemDetailsActivity.class);
////                intent.putExtra("name" , pm.getName());
////                intent.putExtra("price" ,pm.getPrice());
////                intent.putExtra("mySalg",pm.getSlug());
////                view.getContext().startActivity(intent);
//            }
//        });

        }


@Override
public int getItemCount() {
        return dataModelArrayList.size();
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
        ProdModel pm=dataModelArrayList.get(postion);
        Toast.makeText(context, "postion  "+pm.getName(), Toast.LENGTH_SHORT).show();
        prodInterface.setProd(pm);
//        Intent intent = new Intent(context , ItemDetailsActivity.class);
//        intent.putExtra("name" , pm.getName());
//        intent.putExtra("price" ,pm.getPrice());
//        intent.putExtra("mySalg",pm.getSlug());
//        view.getContext().startActivity(intent);
    }
}

}

