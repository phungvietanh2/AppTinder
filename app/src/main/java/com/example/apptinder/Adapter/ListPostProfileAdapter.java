package com.example.apptinder.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Likes;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ListPostProfileAdapter extends RecyclerView.Adapter<ListPostProfileAdapter.ViewHolder> {

    private Context context;
    private  List<Post> posts;

    public ListPostProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ListPostProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listsocial2, parent, false);
        return new ListPostProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPostProfileAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.namepost.setText(post.getContent());

        List<String> imageList = post.getImages();
        if (imageList != null && !imageList.isEmpty()) {
            ArrayList<Uri> uriList = convertUriList(imageList);

            Glide.with(holder.itemView.getContext())
                    .load(uriList.get(0))
                    .placeholder(R.drawable.ic_home)
                    .into(holder.postimage);
        } else {
            holder.postimage.setImageResource(R.drawable.ic_close);
            holder.postimage.setVisibility(View.GONE);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date postDate = null;
        Date currentDate = new Date();

        try {
            postDate = dateFormat.parse(post.getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeDiffMillis = currentDate.getTime() - postDate.getTime();
        long m = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis);
        long h = TimeUnit.MILLISECONDS.toHours(timeDiffMillis);
        long d = TimeUnit.MILLISECONDS.toDays(timeDiffMillis);

        String timeAgo;

        if (m < 60) {
            timeAgo = m + " phút trước";
        } else if (h < 24) {
            timeAgo = h + " giờ trước";
        } else if (d <= 3) {
            timeAgo = d + " ngày trước";
        } else {
            timeAgo = "Cách đây hơn 3 ngày";
        }

        holder.date.setText(timeAgo);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    private ArrayList<Uri> convertUriList(List<String> imageList) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String imageUrl : imageList) {
            Uri uri = Uri.parse(imageUrl);
            uriList.add(uri);
        }
        return uriList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namepost,date;
        private ImageView postimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namepost = itemView.findViewById(R.id.commentTvv11);
            postimage = itemView.findViewById(R.id.idIVcourseIV11);
            date = itemView.findViewById(R.id.datepr);
        }
    }
}
