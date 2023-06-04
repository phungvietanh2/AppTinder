//package com.example.apptinder.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.apptinder.DBcontext.DBcontext;
//import com.example.apptinder.DBcontext.UserDao;
//import com.example.apptinder.Model.Comments;
//import com.example.apptinder.Model.Post;
//import com.example.apptinder.Model.User;
//import com.example.apptinder.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ViewHolder> {
//
//    List<Comments> comments;
//    private List<Post> posts;
//    private Context context;
//    private int postId;
//
//    public ListCommentAdapter(Context context, List<Comments> comments, List<Post> posts) {
//        this.context = context;
//        this.comments = comments;
//        this.posts = posts;
//    }
//
//
//    @NonNull
//    @Override
//    public ListCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.listsocial1, parent, false);
//        return new ListCommentAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ListCommentAdapter.ViewHolder holder, int position) {
//        DBcontext db = DBcontext.getDatabase(context);
//        UserDao userDao = db.userDao();
//        Comments comment = comments.get(position); // Lấy đối tượng Comments từ danh sách
//        User user = userDao.getUserById(comment.getUserId()); // Lấy đối tượng User dựa trên userId từ comments
//
//        holder.viewname.setText(user.getUsername()); // Đặt giá trị cho thành phần hiển thị tên người dùng
//        holder.viewcommen.setText(comment.getContent()); // Đặt giá trị cho thành phần hiển thị nội dung bình luận
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return comments.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView viewname, viewcommen;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            viewname = itemView.findViewById(R.id.commenameEt);
//            viewcommen = itemView.findViewById(R.id.commentTvv);
//        }
//    }
//
//
//}

package com.example.apptinder.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;

import java.util.ArrayList;
import java.util.List;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ViewHolder> {

    private List<Comments> comments;
    private List<Comments> filteredComments; // Danh sách comment đã được lọc theo postId
    private List<Post> posts;
    private Context context;
    private int postId;

    public ListCommentAdapter(Context context, List<Comments> comments, List<Post> posts, int postId) {
        this.context = context;
        this.comments = comments;
        this.posts = posts;
        this.postId = postId;
       filterCommentsByPostId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listsocial1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBcontext db = DBcontext.getDatabase(context);
        UserDao userDao = db.userDao();
        Comments comment = filteredComments.get(position); // Lấy đối tượng Comments từ danh sách đã lọc
        User user = userDao.getUserById(comment.getUserId()); // Lấy đối tượng User dựa trên userId từ comments

        holder.viewname.setText(user.getUsername()); // Đặt giá trị cho thành phần hiển thị tên người dùng
        holder.viewcommen.setText(comment.getContent()); // Đặt giá trị cho thành phần hiển thị nội dung bình luận
    }

    @Override
    public int getItemCount() {
        if (filteredComments != null) {
            return filteredComments.size();
        } else {
            return 0;
        }
    }
    private void filterCommentsByPostId() {
        if (comments != null) {
            filteredComments = new ArrayList<>();
            for (Comments comment : comments) {
                if (comment.getPostId() == postId) {
                    filteredComments.add(comment);
                }
            }
        } else {
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView viewname, viewcommen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewname = itemView.findViewById(R.id.commenameEt);
            viewcommen = itemView.findViewById(R.id.commentTvv);
        }
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
        filterCommentsByPostId();
        notifyDataSetChanged();
    }
}
