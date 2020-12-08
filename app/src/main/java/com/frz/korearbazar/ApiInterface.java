package com.frz.korearbazar;

import com.frz.korearbazar.model.SignInResponse;
import com.frz.korearbazar.model.SignUpResponse;
import com.frz.korearbazar.model.TokenResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ApiInterface {

    String JSONURL = "http://ecom.hrventure.xyz/public/";
    String ProdImgUrl = "assets/images/thumbnails/";
    String SlidersImgUrl = "assets/images/sliders/";
    String BannerImgUrl = "assets/images/banners/";
    String BrandsImgUrl = "assets/images/partner/";
    String ReviewsImgUrl = "assets/images/reviews/";
    String BlogImgUrl = "assets/images/blogs/";
    String PDetailsImgUrl = "assets/images/products/";

    @GET("api/setting")
    Call<String> getString();

    @GET("api")
    Call<String> getProducts();

    @GET("api/extra")
    Call<String> getBestProducts();

//    @GET("api/item/")
////    Call<String> getProductsDetails();

    @GET("api/products")
    Call<String> getProductsDetails();



    @FormUrlEncoded // annotation used in POST type requests
    //@POST("/retrofit/register.php")
    @retrofit.http.POST("/token")// API's endpoints
    @Headers({
            "Accept: application/json",
            "Content-type: application/x-www-form-urlencoded",
    })
    Callback<TokenResponse> getToken(
           // @Field("grant_type") String grant_type,
            @Field("email") String email,
            @Field("password") String password);



    @FormUrlEncoded // annotation used in POST type requests
    //@POST("/retrofit/register.php")
    @retrofit.http.POST("/login")// API's endpoints
    public void login(@retrofit.http.Field("email") String email,
                      @retrofit.http.Field("password") String password,
                      @Field("logintype") String logintype,
                      Callback<SignInResponse> callback);

    @FormUrlEncoded // annotation used in POST type requests
    //@POST("/retrofit/register.php")
    @retrofit.http.POST("/user/register")// API's endpoints
    public void registration(@retrofit.http.Field("name") String name,
                             @retrofit.http.Field("email") String email,
                             @retrofit.http.Field("phone") String phone,
                             @retrofit.http.Field("address") String address,
                             @retrofit.http.Field("password") String password,
                             @retrofit.http.Field("password_confirmation") String password_confirmation,
                             @Field("logintype") String logintype,
                             Callback<SignUpResponse> callback);

//    @POST("login/{email}/{password}")
//    Call login(@Path("email") String username, @Path("password") String password);


}
