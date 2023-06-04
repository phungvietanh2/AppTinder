package com.example.apptinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptinder.CommenActivity;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Post;
import com.example.apptinder.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {
    private List<Post> posts;
    private Context context;

    public List<Comments> comments;

    public ListImageAdapter(Context context, List<Post> posts ,List<Comments> comments) {
        this.context = context;
        this.posts = posts;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listsocial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.shuffle(posts);

        Post post = posts.get(position);
        Comments comments1 = comments.get(position);
        holder.editText.setText(post.getContent());
        List<String> imageList = post.getImages();
        if (imageList != null && !imageList.isEmpty()) {
            ArrayList<Uri> uriList = convertUriList(imageList);

            Glide.with(holder.itemView.getContext())
                    .load(uriList.get(0)) // Load the first image from the Uri list
                    .placeholder(R.drawable.ic_home)
                    .into(holder.imageView);
        } else {
            // If the image list is empty, you can set a default image to the ImageView
            holder.imageView.setImageResource(R.drawable.ic_close);
        }

        holder.Imagecommen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommenActivity.class);
                intent.putExtra("postId", post.getPostId());
                intent.putExtra("post", post);
                context.startActivity(intent);

            }
        });
        int commentCount = getCommentCount(position);
        holder.countcomment.setText(String.valueOf(commentCount));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView editText;
        private ImageView imageView ,Imagecommen;
        private TextView countcomment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.snnameEt);
            imageView = itemView.findViewById(R.id.idIVcourseIV);
            Imagecommen = itemView.findViewById(R.id.CommenIm);
            countcomment = itemView.findViewById(R.id.CountCommenTv);
        }
    }

    private int getCommentCount(int position) {
        int postId = posts.get(position).getPostId();
        int commentCount = 0;
        for (Comments comment : comments) {
            if (comment.getPostId() == postId) {
                commentCount++;
            }
        }
        return commentCount;
    }


    private ArrayList<Uri> convertUriList(List<String> imageList) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String imageUrl : imageList) {
            Uri uri = Uri.parse(imageUrl);
            uriList.add(uri);
        }
        return uriList;

    }

}
