package com.vit.mychat.ui.friends.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.di.scope.PerFragment;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.friends.listener.OnClickFriendOnlineItemListener;
import com.vit.mychat.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@PerFragment
public class FriendOnlineAdapter extends RecyclerView.Adapter<FriendOnlineAdapter.FriendOnlineViewHolder> {

    @Inject
    OnClickFriendOnlineItemListener listener;

    private List<UserViewData> mListFriendOnline = new ArrayList<>();

    @Inject
    public FriendOnlineAdapter() {
    }

    public void setList(List<UserViewData> listFriendOnline) {
        this.mListFriendOnline = listFriendOnline;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendOnlineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_online_item, viewGroup, false);
        return new FriendOnlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendOnlineViewHolder friendOnlineViewHolder, int i) {
        friendOnlineViewHolder.bindData(mListFriendOnline.get(i));
    }

    @Override
    public int getItemCount() {
        return mListFriendOnline.size();
    }

    class FriendOnlineViewHolder extends BaseViewHolder<UserViewData> {
        @BindView(R.id.image_avatar_online)
        ImageView mImageAvatar;

        @BindView(R.id.image_online)
        ImageView mImageOnline;

        @BindView(R.id.text_name)
        TextView mTextName;
        public FriendOnlineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindData(UserViewData userViewData) {

            GlideApp.with(itemView.getContext())
                    .load(userViewData.getAvatar())
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar_user)
                    .into(mImageAvatar);

            if(userViewData.getOnline() == Constants.ONLINE){
                mImageOnline.setVisibility(View.VISIBLE);
            } else {
                mImageOnline.setVisibility(View.GONE);
            }

            mTextName.setText(userViewData.getName());
        }

        @OnClick(R.id.layout_root)
        void onClickItem() {
            listener.onClickFriendOnlineItem(mListFriendOnline.get(getAdapterPosition()));
        }
    }
}
