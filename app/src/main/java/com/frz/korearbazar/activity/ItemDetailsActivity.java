package com.frz.korearbazar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.frz.korearbazar.ApiInterface;
import com.frz.korearbazar.Database.CartDB;
import com.frz.korearbazar.MainActivity;
import com.frz.korearbazar.R;
import com.frz.korearbazar.adapter.ProdDetailsAdapter;
import com.frz.korearbazar.model.BestSellerModel;
import com.frz.korearbazar.model.CartModel;
import com.frz.korearbazar.model.ProdDetailsModel;
import com.frz.korearbazar.model.ProdModel;
import com.frz.korearbazar.model.SlidersModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.frz.korearbazar.ApiInterface.JSONURL;
import static com.frz.korearbazar.ApiInterface.PDetailsImgUrl;


public class ItemDetailsActivity extends AppCompatActivity {
   ProdDetailsAdapter prodDetailsAdapter;
   LinearLayout linearLayout;

   ProdModel prodModel;
   BestSellerModel bestSellerModel;
   CartDB cartDB;
   String imgUrl = "";

    ImageView imgBack;
    ImageView imgCart;
    TextView txtTcount;
    RelativeLayout lvlCart;
    TextView txtTitle;
    TextView txtDesc;
    TextView btnAddtocart,buyNow,continue_shopping;
    //String Uri="http://ecom.hrventure.xyz/carts";

    TextView txtPrice;
    TextView txtItemOffer;
    TextView txtSeler;
    ImageView plusquantity, minusquantity;
    int quantity = 1;
    TextView quantitynumber;
    String slug;

    //Slider
    ImageView imgDtails;
    private ProdDetailsAdapter sliderAdapter;
    private RecyclerView sliderRecyclerView;
    List<SlidersModel> sliderDatumList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        cartDB = new CartDB(this);

        imgBack = findViewById(R.id.img_back);
        imgCart = findViewById(R.id.img_cart);
        txtTcount = findViewById(R.id.txt_tcount);
        lvlCart = findViewById(R.id.lvl_cart);
        txtTitle = findViewById(R.id.txt_title);
        txtDesc = findViewById(R.id.txt_desc);
        txtPrice = findViewById(R.id.txt_price);
        btnAddtocart = findViewById(R.id.addToCart);
        buyNow = findViewById(R.id.buyNow);
        continue_shopping = findViewById(R.id.continue_shopping);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity  = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);


        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prodModel.getPrice();
                Toast.makeText(ItemDetailsActivity.this, ""+prodModel.getPrice(), Toast.LENGTH_SHORT).show();

                double basePrice = Double.parseDouble((prodModel.getPrice()));
                quantity++;
                displayQuantity();
                double productPrice = basePrice * quantity;
                String setnewPrice = String.valueOf(productPrice);
                txtPrice.setText(setnewPrice);

                Log.e("new price",setnewPrice);
                Toast.makeText(ItemDetailsActivity.this, ""+setnewPrice, Toast.LENGTH_SHORT).show();
            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double basePrice = Double.parseDouble((prodModel.getPrice()));
                // because we dont want the quantity go less than 0
                if (quantity == 1) {
                    Toast.makeText(ItemDetailsActivity.this, "Cant Cart quantity < 1", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    double productPrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(productPrice);
                    txtPrice.setText(setnewPrice);
                }
            }
        });

        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemDetailsActivity.this, MainActivity.class));
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemDetailsActivity.this, MainActivity.class));
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemDetailsActivity.this, "Buy Now", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://ecom.hrventure.xyz/carts"));
//                startActivity(intent);
            }
        });

        btnAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ItemDetailsActivity.this, CartActivity.class));

                SaveCart();
            }
        });



        txtPrice = findViewById(R.id.txt_price);
        txtItemOffer = findViewById(R.id.txt_item_offer);
        txtSeler = findViewById(R.id.txt_seler);




        Bundle intent = getIntent().getExtras();
        if (intent!=null){
            prodModel = (ProdModel) intent.getSerializable("prodctModel");
            //bestSellerModel = (BestSellerModel) intent.getSerializable("bestSellerModel");
            Toast.makeText(this, ""+ prodModel.getSlug(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, ""+ bestSellerModel.getSlug(), Toast.LENGTH_SHORT).show();
        }

        //Slider
        imgDtails =findViewById(R.id.imgDtails);
        //sliderRecyclerView = findViewById(R.id.recyclerDSlider);
        //sliderfetchJSON();


        linearLayout = findViewById(R.id.linearLayout1);
//
        ProdDetailsfetchJSON();

    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }

    private void SaveCart() {

        // getting the values from our views
        String name = txtTitle.getText().toString();
        String price = txtPrice.getText().toString();
        String quantity = quantitynumber.getText().toString();

        CartModel cartModel = new CartModel(name,price,quantity,imgUrl);
        long insertData=   cartDB.addInsert(cartModel);
        if (insertData>0){
            Toast t = Toast.makeText( getApplicationContext(), "Successfully Added to Cart!" + insertData, Toast.LENGTH_LONG );
            t.show();
        }else {
            Toast t = Toast.makeText( getApplicationContext(), "Successfully not Added to Cart!", Toast.LENGTH_LONG );
            t.show();
        }

    }

    private void ProdDetailsfetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getProductsDetails();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();

                        prod_details_writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ItemDetailsActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prod_details_writeRecycler(String jsonresponse) {
        try {
            JSONObject object = new JSONObject(jsonresponse);

                ArrayList<ProdDetailsModel> prodDetailsmodelRecyclerArrayList = new ArrayList<>();

                JSONArray jsonArray = object.getJSONArray("products");

                //Toast.makeText(this, "Done" + jsonArray, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < jsonArray.length(); i++) {

                    ProdDetailsModel prodDetalisModelRecycler = new ProdDetailsModel();
                    JSONObject dataobj = jsonArray.getJSONObject(i);


                    String slugf=dataobj.getString("slug");
                     if (slugf.equals(prodModel.getSlug())){
                         prodDetalisModelRecycler.setSlug(dataobj.getString("slug"));
                         prodDetalisModelRecycler.setPhoto(dataobj.getString("photo"));
                         String photo=dataobj.getString("photo");
                         //prodDetalisModelRecycler.setGalleries(dataobj.getString("galleries"));
                         prodDetalisModelRecycler.setName(dataobj.getString("name"));
                         prodDetalisModelRecycler.setDetails(dataobj.getString("details"));
                         prodDetalisModelRecycler.setPrice(dataobj.getString("price"));
                         prodDetalisModelRecycler.setStock(dataobj.getString("stock"));
                         prodDetalisModelRecycler.setSlug(dataobj.getString("slug"));


                         txtTitle.setText(dataobj.getString("name"));
                         txtPrice.setText(dataobj.getString("price"));
                         txtDesc.setText(dataobj.getString("details"));
                         //Picasso.get().load("http://ecom.hrventure.xyz/assets/images/products/1606560895gDvz8eUj.png").into(imgDtails);
                         imgUrl = JSONURL+PDetailsImgUrl+photo;
                         Picasso.get().load(imgUrl).into(imgDtails);


                     }

//                    sliderAdapter = new ProdDetailsAdapter(this,prodDetailsmodelRecyclerArrayList);
//                    sliderRecyclerView.setAdapter(sliderAdapter);
//                    sliderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


                }

        }
            catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void updateItem() {
//        Cursor res = databaseHelper.getAllData();
//        if (res.getCount() == 0) {
//            txtTcount.setText("0");
//        } else {
//            txtTcount.setText("" + res.getCount());
//        }
//    }
//
//    @OnClick({R.id.img_back, R.id.lvl_cart, R.id.btn_addtocart})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
//            case R.id.lvl_cart:
//                fragment();
//                break;
//            case R.id.btn_addtocart:
//                finish();
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void setJoinPlayrList(LinearLayout lnrView, ProductItem datum, Price price) {
//
//        lnrView.removeAllViews();
//        final int[] count = {0};
//        DatabaseHelper helper = new DatabaseHelper(lnrView.getContext());
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view = inflater.inflate(R.layout.custome_additem, null);
//        TextView txtcount = view.findViewById(R.id.txtcount);
//        LinearLayout lvl_addremove = view.findViewById(R.id.lvl_addremove);
//        LinearLayout lvl_addcart = view.findViewById(R.id.lvl_addcart);
//        LinearLayout img_mins = view.findViewById(R.id.img_mins);
//        LinearLayout img_plus = view.findViewById(R.id.img_plus);
//        MyCart myCart = new MyCart();
//        myCart.setPid(datum.getId());
//        myCart.setImage(datum.getProductImage());
//        myCart.setTitle(datum.getProductName());
//        myCart.setWeight(price.getProductType());
//        myCart.setCost(price.getProductPrice());
//        myCart.setDiscount(datum.getmDiscount());
//        int qrt = helper.getCard(myCart.getPid(), myCart.getCost());
//        if (qrt != -1) {
//            count[0] = qrt;
//            txtcount.setText("" + count[0]);
//            lvl_addremove.setVisibility(View.VISIBLE);
//            lvl_addcart.setVisibility(View.GONE);
//        } else {
//            lvl_addremove.setVisibility(View.GONE);
//            lvl_addcart.setVisibility(View.VISIBLE);
//
//        }
//        img_mins.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                count[0] = Integer.parseInt(txtcount.getText().toString());
//
//                count[0] = count[0] - 1;
//                if (count[0] <= 0) {
//                    lvl_addremove.setVisibility(View.GONE);
//                    lvl_addcart.setVisibility(View.VISIBLE);
//                    txtcount.setText("0");
//                    helper.deleteRData(myCart.getPid(), myCart.getCost());
//                } else {
//                    txtcount.setVisibility(View.VISIBLE);
//                    txtcount.setText("" + count[0]);
//                    myCart.setQty(String.valueOf(count[0]));
//                    helper.insertData(myCart);
//                }
//                updateItem();
//                if (itemListFragment != null)
//                    itemListFragment.updateItem();
//            }
//        });
//
//        img_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                count[0] = Integer.parseInt(txtcount.getText().toString());
//
//                count[0] = count[0] + 1;
//                txtcount.setText("" + count[0]);
//                myCart.setQty(String.valueOf(count[0]));
//                Log.e("INsert", "--> " + helper.insertData(myCart));
//                updateItem();
//                if (itemListFragment != null)
//                    itemListFragment.updateItem();
//            }
//        });
//        lvl_addcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lvl_addcart.setVisibility(View.GONE);
//                lvl_addremove.setVisibility(View.VISIBLE);
//                count[0] = Integer.parseInt(txtcount.getText().toString());
//
//                count[0] = count[0] + 1;
//                txtcount.setText("" + count[0]);
//                myCart.setQty(String.valueOf(count[0]));
//                Log.e("INsert", "--> " + helper.insertData(myCart));
//                updateItem();
//                if (itemListFragment != null)
//                    itemListFragment.updateItem();
//            }
//        });
//        lnrView.addView(view);
//
//    }
//
//    public void fragment() {
//        SessionManager.iscart = true;
//        finish();
//
//    }
//



//    public class MyCustomPagerAdapter extends PagerAdapter {
//        Context context;
//        ArrayList<ProdDetailsModel> imageList;
//        LayoutInflater layoutInflater;
//
//        public MyCustomPagerAdapter(Context context, ArrayList<ProdDetailsModel> bannerDatumList) {
//            this.context = context;
//            this.imageList = bannerDatumList;
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        }
//
//        @Override
//        public int getCount() {
//            return imageList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == ((LinearLayout) object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            View itemView = layoutInflater.inflate(R.layout.item_image, container, false);
//            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            //txtTitle.setText(txtTitle.getText());
//           // holder.price.setText(dataModelArrayList.get(position).getPrice());
//           // holder.previous_price.setText(dataModelArrayList.get(position).getPrevious_price());
//            Glide.with(ItemDetailsActivity.this).load(JSONURL+ProdImgUrl + imageList.get(position)).placeholder(R.drawable.empty).into(imageView);
//            container.addView(itemView);
//
//            return itemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((LinearLayout) object);
//        }
//    }
}
