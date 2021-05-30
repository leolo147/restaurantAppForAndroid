package com.leolo.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.Comments;
import com.leolo.restaurantapp.API.CreateUserResponse;
import com.leolo.restaurantapp.API.FavoriteResponseWithId;
import com.leolo.restaurantapp.API.Favorites;
import com.leolo.restaurantapp.API.FavoritesResquestModel;
import com.leolo.restaurantapp.API.restaurants;
import com.leolo.restaurantapp.adapter.AsiaFoodAdapter;
import com.leolo.restaurantapp.adapter.CommentAdapter;
import com.leolo.restaurantapp.adapter.GlobalVariable;
import com.leolo.restaurantapp.model.AsiaFood;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    TextView resNameTextView,dirTextView,fullAddressTextView,phoneTextView;
    ImageView returnImageView,favImageView,resImageView;
    EditText post_detail_comment;
    Button post_detail_add_comment_btn;
    Context context;
    boolean isfarorite = false;
    APIInterface apiInterface;
    GlobalVariable gv;
    String favorites_id;
    RecyclerView rv_comment;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        AsiaFood asiaFood = (AsiaFood)getIntent().getSerializableExtra("asiaFood");
        gv = (GlobalVariable)getApplicationContext();
        apiInterface = APIClient.getClient().create(APIInterface.class);

        resNameTextView = findViewById(R.id.resNameTextView);
        dirTextView = findViewById(R.id.dirTextView);
        fullAddressTextView = findViewById(R.id.fullAddressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);

        resImageView = findViewById(R.id.resImageView);
        returnImageView = findViewById(R.id.returnImageView);
        favImageView = findViewById(R.id.favImageView);

        post_detail_comment = findViewById(R.id.post_detail_comment);

        post_detail_add_comment_btn = findViewById(R.id.post_detail_add_comment_btn);

        rv_comment = findViewById(R.id.rv_comment);

        resNameTextView.setText(asiaFood.getRestaurantName());
        dirTextView.setText("District : "+asiaFood.getDistrict());
        fullAddressTextView.setText(asiaFood.getFull_address());
        phoneTextView.setText("Phone no.: "+asiaFood.getPhone());
        Picasso.with(context).load(asiaFood.getImgUrl()).into(resImageView);

        List<Comments.comment> commentList = new ArrayList<>();
        Comments comments = new Comments();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Comments> call = apiInterface.getUserComments(asiaFood.getRestaurantName());
        call.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                for(Comments.comment comment : response.body().comments) {
                    Log.d("TAG123",comment.content+"");
                    commentList.add(comment);
                }
                setCommentRecycler(commentList);
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {
                Log.d("fail","leolo123"+ t.getMessage());
                call.cancel();
            }
        });

        if(gv.isLogin()) {


            Call<Favorites> favoritesCall = apiInterface.getUserFavorites(gv.getToken(), gv.getUsername());
            favoritesCall.enqueue(new Callback<Favorites>() {
                @Override
                public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                    if (!response.equals("")) {
                        for (Favorites.favorite favorite : response.body().favorites) {
                            Log.d("favorite_resName", favorite.restaurant_name + "");
                            if (favorite.restaurant_name.equals(resNameTextView.getText().toString())) {
                                isfarorite = true;
                                favImageView.setImageResource(R.drawable.ic_heart_border);
                                favorites_id = favorite.id;
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<Favorites> call, Throwable t) {
                    Log.d("fail", "leolo123" + t.getMessage());
                    call.cancel();
                }
            });
        }

        favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gv.isLogin()) {
                    if(isfarorite){
                        favImageView.setImageResource(R.drawable.ic_heart);
                        isfarorite = false;

                        Call<CreateUserResponse> call = apiInterface.removeFavorite(gv.getToken(),favorites_id);
                        call.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                System.out.println("status "+response.body().message);
                            }

                            @Override
                            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                                Log.d("fail", "leolo123" + t.getMessage());
                                call.cancel();
                            }
                        });
                    }else {
                        favImageView.setImageResource(R.drawable.ic_heart_border);
                        isfarorite = true;

                        FavoritesResquestModel favoritesResquestModel = new FavoritesResquestModel(gv.getUsername(),asiaFood.getRestaurantName());

                        Call<FavoriteResponseWithId> call = apiInterface.addFavorite(favoritesResquestModel,gv.getToken());
                        call.enqueue(new Callback<FavoriteResponseWithId>() {
                            @Override
                            public void onResponse(Call<FavoriteResponseWithId> call, Response<FavoriteResponseWithId> response) {
                                System.out.println("status "+response.body().message);
                                favorites_id = response.body().id;
                            }

                            @Override
                            public void onFailure(Call<FavoriteResponseWithId> call, Throwable t) {
                                Log.d("fail", "leolo123" + t.getMessage());
                                call.cancel();
                            }
                        });

                    }
                }
            }
        });

        post_detail_add_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = post_detail_comment.getText().toString();
                if(gv.isLogin()) {
                    if(content!=null){
                        Comments.comment comment = new Comments.comment(gv.getUsername(),asiaFood.getRestaurantName(),content);

                        Call<FavoriteResponseWithId> call = apiInterface.addComment(comment,gv.getToken());
                        call.enqueue(new Callback<FavoriteResponseWithId>() {
                            @Override
                            public void onResponse(Call<FavoriteResponseWithId> call, Response<FavoriteResponseWithId> response) {
                                System.out.println("status "+response.body().message);
                                comment.setId(response.body().id);
                                commentList.add(comment);
                                commentAdapter.notifyDataSetChanged();
                                setCommentRecycler(commentList);
                            }

                            @Override
                            public void onFailure(Call<FavoriteResponseWithId> call, Throwable t) {
                                Log.d("fail", "leolo123" + t.getMessage());
                                call.cancel();
                            }
                        });
                    }
                }else{
                    Toast.makeText(context, "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCommentRecycler(List<Comments.comment> commentList) {

        //rv_comment = findViewById(R.id.rv_comment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_comment.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(getApplicationContext(),commentList);
        rv_comment.setAdapter(commentAdapter);

    }
}
