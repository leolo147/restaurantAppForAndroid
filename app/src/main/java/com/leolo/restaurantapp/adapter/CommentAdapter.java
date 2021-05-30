package com.leolo.restaurantapp.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.Comments;
import com.leolo.restaurantapp.API.CreateUserResponse;
import com.leolo.restaurantapp.R;
import com.leolo.restaurantapp.model.AsiaFood;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    Context context;
    List<Comments.comment> comments;
    GlobalVariable gv;
    APIInterface apiInterface;

    public CommentAdapter(Context context, List<Comments.comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Long timeStamp;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        gv = (GlobalVariable)context.getApplicationContext();

        if(comments.get(position).timestamp instanceof Double){
            timeStamp = Math.round((double)comments.get(position).timestamp);
        }else{
            timeStamp = (Long)comments.get(position).timestamp;
        }

        holder.tv_name.setText(comments.get(position).getUsername());
        holder.tv_content.setText(comments.get(position).getContent());
        holder.tv_date.setText(timestampToString(timeStamp));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gv.isLogin()) {
                    if (comments.get(position).getUsername().equals(gv.getUsername())) {
                        Log.e("tes", gv.getUsername());
                        Call<CreateUserResponse> call = apiInterface.removeComment(gv.getToken(), comments.get(position).getId());
                        call.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                System.out.println("status " + response.body().message);
                                comments.remove(position);
                                notifyItemRemoved(position);
                            }

                            @Override
                            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                                Log.d("fail", "leolo123" + t.getMessage());
                                call.cancel();
                            }
                        });
                    } else {
                        Toast.makeText(context, "you can not delete which is not your!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static final class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_content,tv_date;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.commentUsername);
            tv_content = itemView.findViewById(R.id.userComment);
            tv_date = itemView.findViewById(R.id.dateComment);



        }
    }

    private String timestampToString(long time){
        Calendar calendar = Calendar.getInstance(Locale.TRADITIONAL_CHINESE);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm",calendar).toString();
        return date;
    }
}
