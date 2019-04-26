package com.vit.mychat.ui.message.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageViewData> mMessageList = new ArrayList<>();
    private UserViewData mUser;

    private int selectedPosition = 100;

    @Inject
    public MessageAdapter() {
    }

    public void setList(List<MessageViewData> messageList) {
        this.mMessageList = messageList;
        notifyDataSetChanged();
    }

    public void setUser(UserViewData userViewData) {
        this.mUser = userViewData;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.bindData(mMessageList.get(i));

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class MessageViewHolder extends BaseViewHolder<MessageViewData> {

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.text_message)
        TextView mTextMessage;

        @BindView(R.id.text_seen)
        TextView mTextSeen;

        @BindView(R.id.text_time)
        TextView mTextTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(MessageViewData message) {

            GlideApp.with(itemView)
                    .load(mUser.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);

            mTextMessage.setText(message.getMessage());
            mTextSeen.setText(message.isSeen() ? "Đã xem" : "");
            mTextTime.setText(String.valueOf(message.getTime()));

            if (selectedPosition != getLayoutPosition()) {
                mTextTime.setVisibility(View.GONE);
                mTextSeen.setVisibility(View.GONE);
            }
        }
    }
}
