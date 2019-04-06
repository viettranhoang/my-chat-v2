package com.vit.mychat.ui.friends.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.data.model.Friend;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FriendNewsAdapter  extends RecyclerView.Adapter<FriendNewsAdapter.FriendNewsViewHolder> {
    private List<Friend> mListFriendNews = new ArrayList<>();

    public FriendNewsAdapter(List<Friend> mListFriendNews) {
        this.mListFriendNews = mListFriendNews;
    }

    @NonNull
    @Override
    public FriendNewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_news_item, viewGroup,false);
        return new FriendNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendNewsViewHolder friendNewsViewHolder, int i) {
            friendNewsViewHolder.bindData(mListFriendNews.get(i));
    }

    @Override
    public int getItemCount() {
        return mListFriendNews.size();
    }

    public class FriendNewsViewHolder extends BaseViewHolder<Friend> {
        @BindView(R.id.image_avatar_news)
        ImageView mImageAvatar;

        @BindView(R.id.image_friend_news)
        ImageView mImageFriendNews;

        @BindView(R.id.text_name)
        TextView mTextName;

        public FriendNewsViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void bindData(Friend friend) {
            GlideApp.with(itemView.getContext())
                    .load(friend.getNews())
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(mImageFriendNews);
            GlideApp.with(itemView.getContext())
                    .load(friend.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);
            mTextName.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            mTextName.setText(friend.getName());
        }


    }
}
