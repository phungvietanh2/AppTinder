package com.example.apptinder.Adapter;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptinder.ChatmessgengerActivity;
import com.example.apptinder.CommenActivity;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.LikeDao;
import com.example.apptinder.Fragment.FragmentSetting;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Likes;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.ProfileActivity;
import com.example.apptinder.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {
    private List<Post> posts;
    private Context context;

    public List<Comments> comments;

    public List<Likes> likes;

    private LikeDao likeDao;
    private DBcontext db;
    private int usaerid;

    private List<User> users;


    public ListImageAdapter(Context context, List<Post> posts, List<Comments> comments, List<Likes> likes, int usaerid, List<User> user) {
        this.context = context;
        this.posts = posts;
        this.comments = comments;
        this.likes = likes;
        this.usaerid = usaerid;
        this.users = user;
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

        Post post = posts.get(position);

        holder.editText.setText(post.getContent());

        List<String> imageList = post.getImages();
        if (imageList != null && !imageList.isEmpty()) {
            ArrayList<Uri> uriList = convertUriList(imageList);

            Glide.with(holder.itemView.getContext())
                    .load(uriList.get(0))
                    .placeholder(R.drawable.ic_home)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_close);
            holder.imageView.setVisibility(View.GONE);
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

        holder.likeCount = getLikeCount(post);
        holder.likecommen.setText(String.valueOf(holder.likeCount));

        boolean isLiked = isPostLiked(post);


        if (isLiked) {
            holder.likeimagecommen.setImageResource(R.drawable.ic_favorite_like);
        } else {
            holder.likeimagecommen.setImageResource(R.drawable.ic_favorite_border_24);
        }

        db = DBcontext.getDatabase(context);
        likeDao = db.likeDao();

        holder.likeimagecommen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLiked = isPostLiked(post);

                if (isLiked) {
                    holder.likeimagecommen.setImageResource(R.drawable.ic_favorite_border_24);
                    setPostLiked(post, false);
                    holder.likeCount--;
                    holder.likecommen.setText(String.valueOf(holder.likeCount));

                    Likes like = new Likes();
                    like.setPostId(post.getPostId());
                    like.setUserId(usaerid);
                    int postId = post.getPostId();
                    int userId = usaerid;
                    likeDao.deleteLikeByPostAndUser(postId, userId);

                    for (Likes likeItem : likes) {
                        if (likeItem.getPostId() == postId && likeItem.getUserId() == userId) {
                            likes.remove(likeItem);
                            break;
                        }
                    }
                } else {
                    holder.likeimagecommen.setImageResource(R.drawable.ic_favorite_like);
                    setPostLiked(post, true);
                    holder.likeCount++;
                    holder.likecommen.setText(String.valueOf(holder.likeCount));

                    Likes like = new Likes();
                    like.setPostId(post.getPostId());
                    like.setUserId(usaerid);
                    likeDao.insertlistlike(like);

                    likes.add(like);
                }

                notifyItemChanged(position);
            }
        });


        int userId = post.getUserId();
        User user = null;

        for (User u : users) {
            if (u.getUserId() == userId) {
                user = u;
                break;
            }
        }

        if (user != null) {
            holder.namesnTv.setText(user.getUsername());
             int userId1 = user.getUserId();
            String avatar = user.getAvatar();
            try {
                if (avatar != null ) {
                    Uri avatarUri = Uri.parse(avatar);
                    holder.profileIv.setImageURI(avatarUri);
                } else {
                    holder.profileIv.setImageResource(R.drawable.ic_close);
                }
                holder.profileIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(usaerid == userId1){
                            //chua lam j
                        }else {
                            Intent intent = new Intent(context, ProfileActivity.class);
                            intent.putExtra("useargetid", userId1);
                            context.startActivity(intent);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý nếu có lỗi khi đặt hình ảnh
            }
        } else {
            // Xử lý nếu không tìm thấy người dùng
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

       holder.datestart.setText(timeAgo);



    }




    @Override
    public int getItemCount() {
        return posts.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView editText ,datestart;
    private ImageView imageView, Imagecommen, likeimagecommen;
    private TextView countcomment, likecommen, namesnTv;
    private int likeCount;

    private CircleImageView profileIv;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.snnameEt);
        imageView = itemView.findViewById(R.id.idIVcourseIV);
        Imagecommen = itemView.findViewById(R.id.CommenIm);
        countcomment = itemView.findViewById(R.id.CountCommenTv);
        likecommen = itemView.findViewById(R.id.loveTv);
        likeimagecommen = itemView.findViewById(R.id.loveIm);
        namesnTv = itemView.findViewById(R.id.namesnTv);
        profileIv = itemView.findViewById(R.id.imageViewAvatar);
        datestart = itemView.findViewById(R.id.datestart);

    }

}

    private int getLikeCount(Post post) {
        int postId = post.getPostId();
        int likeCount = 0;
        for (Likes like : likes) {
            if (like.getPostId() == postId) {
                likeCount++;
            }
        }
        return likeCount;
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


    private void setPostLiked(Post post, boolean isLiked) {
        post.setLiked(isLiked);
        notifyDataSetChanged();
    }

    private boolean isPostLiked(Post post) {
        for (Likes like : likes) {
            if (like.getPostId() == post.getPostId() && like.getUserId() == usaerid) {
                return true;
            }
        }
        return false;
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

