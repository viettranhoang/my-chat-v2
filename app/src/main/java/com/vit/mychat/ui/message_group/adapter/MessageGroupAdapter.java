package com.vit.mychat.ui.message_group.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageGroupAdapter extends RecyclerView.Adapter<MessageGroupAdapter.MessageViewHolder> {

    private List<MessageViewData> mMessageList = new ArrayList<>();
    private GroupViewData mGroup;

    private int selectedPosition = -100;

    @Inject
    public MessageGroupAdapter() {
    }

    public void setList(List<MessageViewData> messageList) {
        this.mMessageList = messageList;
        notifyDataSetChanged();
    }

    public void setGroup(GroupViewData groupViewData) {
        this.mGroup = groupViewData;
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

        @BindView(R.id.text_message_you)
        TextView mTextMessageYou;

        @BindView(R.id.text_message_me)
        TextView mTextMessageMe;

        @BindView(R.id.text_seen_me)
        TextView mTextSeenMe;

        @BindView(R.id.text_seen_you)
        TextView mTextSeenYou;

        @BindView(R.id.text_time)
        TextView mTextTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(MessageViewData message) {

            GlideApp.with(itemView)
                    .load(mGroup.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);

            mTextTime.setText(String.valueOf(message.getTime()));
            mTextSeenMe.setText(message.isSeen() ? "Đã xem" : "Đã chuyển");
            mTextSeenYou.setText(message.isSeen() ? "Đã xem" : "Đã chuyển");
            mTextMessageMe.setText(message.getMessage());
            mTextMessageYou.setText(message.getMessage());


            if (Constants.CURRENT_UID.equals(message.getFrom())) {
                mImageAvatar.setVisibility(View.GONE);
                mTextMessageYou.setVisibility(View.GONE);
                mTextMessageMe.setVisibility(View.VISIBLE);

            } else {
                mImageAvatar.setVisibility(View.VISIBLE);
                mTextMessageYou.setVisibility(View.VISIBLE);
                mTextMessageMe.setVisibility(View.GONE);
            }

            if (selectedPosition != getLayoutPosition()) {
                mTextTime.setVisibility(View.GONE);
                mTextSeenYou.setVisibility(View.GONE);
                mTextSeenMe.setVisibility(View.GONE);
                mTextMessageYou.setBackgroundResource(R.drawable.round_corner_gray_18);
                mTextMessageMe.setBackgroundResource(R.drawable.round_corner_blue_18);
            }
        }

        @OnClick(R.id.text_message_me)
        void onClickMessageMe() {
            if (mTextTime.getVisibility() != View.VISIBLE) {
                selectedPosition = getAdapterPosition();
                Log.i("MMMM", "onClickMessageMe: " + selectedPosition);
                mTextSeenMe.setVisibility(View.VISIBLE);
                mTextTime.setVisibility(View.VISIBLE);
                mTextMessageMe.setBackgroundResource(R.drawable.round_corner_blue_dark_18);
            } else selectedPosition = -100;

            notifyDataSetChanged();
        }

        @OnClick(R.id.text_message_you)
        void onClickMessageYou() {
            if (mTextTime.getVisibility() != View.VISIBLE) {
                selectedPosition = getAdapterPosition();
                Log.i("MMMM", "onClickMessageYou: " + selectedPosition);
                mTextSeenYou.setVisibility(View.VISIBLE);
                mTextTime.setVisibility(View.VISIBLE);
                mTextMessageYou.setBackgroundResource(R.drawable.round_corner_gray_dark_18);
            } else selectedPosition = -100;

            notifyDataSetChanged();
        }


    }
}
