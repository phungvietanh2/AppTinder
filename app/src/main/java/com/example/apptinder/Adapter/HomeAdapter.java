package com.example.apptinder.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<User> users;
    private Context context;

    public HomeAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_acount_home, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    private List<Integer> usedPositions = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Random random = new Random();
        int randomPosition = getRandomUnusedPosition(random);
        usedPositions.add(randomPosition);

        User user = users.get(randomPosition);
        String avatar = user.getAvatar();

        if (avatar != null) {
            Uri avatarUri = Uri.parse(avatar);
            holder.profileIv.setImageURI(avatarUri);
        } else {
            holder.profileIv.setImageResource(R.drawable.ic_close);
        }
        holder.nameaccount.setText(user.getUsername());
        holder.texxt.setText(user.getText());


    }
    public void refreshData(List<User> refreshedUsers) {
        users.clear();
        users.addAll(refreshedUsers);
        usedPositions.clear();
        notifyDataSetChanged();
    }


    private int getRandomUnusedPosition(Random random) {
        int randomPosition;
        do {
            randomPosition = random.nextInt(users.size());
        } while (usedPositions.contains(randomPosition));
        return randomPosition;
    }





    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileIv;
        private TextView nameaccount, texxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIv = itemView.findViewById(R.id.imageViewAvatarhome);
            nameaccount = itemView.findViewById(R.id.commenameEthome);
            texxt = itemView.findViewById(R.id.commentTvvhome);
        }
    }
}
