package com.vit.mychat.ui.friends.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vit.mychat.data.model.Friend;
import com.vit.mychat.ui.base.BaseViewHolder;

public class FriendOnlineAdapter extends RecyclerView.Adapter<FriendOnlineAdapter.FriendOnlineViewHolder> {

    @NonNull
    @Override
    public FriendOnlineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendOnlineViewHolder friendOnlineViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FriendOnlineViewHolder extends BaseViewHolder<Friend> {
        public FriendOnlineViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Friend friend) {

        }
    }
}
