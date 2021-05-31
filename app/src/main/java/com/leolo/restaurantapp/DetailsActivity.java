package com.leolo.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.Comments;
import com.leolo.restaurantapp.API.CreateUserResponse;
import com.leolo.restaurantapp.API.FavoriteResponseWithId;
import com.leolo.restaurantapp.API.Favorites;
import com.leolo.restaurantapp.API.FavoritesResquestModel;
import com.leolo.restaurantapp.API.restaurants;
import com.leolo.restaurantapp.FirebaseDao.DaoComment;
import com.leolo.restaurantapp.FirebaseDao.DaoFavorite;
import com.leolo.restaurantapp.FirebaseModel.Comment;
import com.leolo.restaurantapp.FirebaseModel.Favorite;
import com.leolo.restaurantapp.FirebaseModel.Restaurants;
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
    DaoFavorite daoFavorite;
    DaoComment daoComment;
    AsiaFood asiaFood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        asiaFood = (AsiaFood)getIntent().getSerializableExtra("asiaFood");
        gv = (GlobalVariable)getApplicationContext();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        daoFavorite = new DaoFavorite();
        daoComment = new DaoComment();

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
        dirTextView.setText(getResources().getString(R.string.districh)+asiaFood.getDistrict());
        String addressText = "<font color='black'>"+getResources().getString(R.string.address)+" </font><font color='blue'>"+asiaFood.getFull_address().toString()+"</font>";
        fullAddressTextView.setText(Html.fromHtml(addressText), TextView.BufferType.SPANNABLE);
        fullAddressTextView.setMovementMethod(LinkMovementMethod.getInstance());
        String phoneText = "<font color='black'>"+getResources().getString(R.string.phone)+"</font><font color='blue'>"+asiaFood.getPhone().toString()+"</font>";
        phoneTextView.setText(Html.fromHtml(phoneText), TextView.BufferType.SPANNABLE);
        phoneTextView.setMovementMethod(LinkMovementMethod.getInstance());
        Picasso.with(context).load(asiaFood.getImgUrl()).into(resImageView);


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        getComment();

        if(gv.isLogin()) {
            // if already login, check favorite or not
            daoFavorite.getByUsername(gv.getUsername()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data : snapshot.getChildren()){
                        Favorite favorite = data.getValue(Favorite.class);
                        if(favorite.getRestaurant_name().equals(asiaFood.getRestaurantName())){
                            isfarorite = true;
                            favImageView.setImageResource(R.drawable.ic_heart_border);
                            Log.d("getByUsername",data.getKey());
                            favorites_id = data.getKey();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        fullAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to google map view
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("asiaFood",asiaFood);
                startActivity(i);
            }
        });

        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to phone call view
                String phoneNum = asiaFood.getPhone().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneNum));
                startActivity(callIntent);
            }
        });

        favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if already login, do add favorite/remove favorite
                if(gv.isLogin()) {
                    if(isfarorite){
                        favImageView.setImageResource(R.drawable.ic_heart);
                        isfarorite = false;
                        removeFavorite();
                    }else {
                        favImageView.setImageResource(R.drawable.ic_heart_border);
                        isfarorite = true;

                        addFavorite();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        post_detail_add_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = post_detail_comment.getText().toString();
                // if already login, add comment
                if(gv.isLogin()) {
                    if(content!=""){
                        addComment();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCommentRecycler(List<Comment> commentList) {
        //rv_comment = findViewById(R.id.rv_comment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_comment.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(getApplicationContext(),commentList);
        rv_comment.setAdapter(commentAdapter);

    }

    private void addFavorite(){
        Favorite favorite = new Favorite(gv.getUsername(),asiaFood.getRestaurantName());
        Log.d("paul",favorite.getRestaurant_name()+favorite.getUsername());
        daoFavorite.add(favorite).addOnSuccessListener(suc ->{
            Log.d("add favorites","successful");
        }).addOnFailureListener(er->{
            Log.d("paul",er.getMessage());
        });
    }

    private void removeFavorite(){
        daoFavorite.remove(favorites_id).addOnSuccessListener(suc ->{
            Log.d("remove favorites","successful");
        }).addOnFailureListener(er->{
            Log.d("paul",er.getMessage());
        });
    }

    private void addComment(){
        Comment comment = new Comment(gv.getUsername(),asiaFood.getRestaurantName(),post_detail_comment.getText().toString());
        daoComment.add(comment).addOnSuccessListener(suc ->{
            Log.d("add comment","successful");
        }).addOnFailureListener(er->{
            Log.d("paul",er.getMessage());
        });

    }

    private void getComment(){
        daoComment.getByRestaurantName(asiaFood.getRestaurantName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comment> comments = new ArrayList<>();;
                for(DataSnapshot data : snapshot.getChildren()){
                    Comment comment = data.getValue(Comment.class);
                    comment.setKey(data.getKey());
                    comments.add(comment);
                }
                setCommentRecycler(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fail",error.getMessage());
            }
        });
    }


}
