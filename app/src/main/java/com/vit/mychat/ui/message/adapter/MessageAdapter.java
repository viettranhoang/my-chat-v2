package com.vit.mychat.ui.message.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.message.config.MessageTypeConfig;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.Constants;
import com.vit.mychat.util.RoundedCornersTransformation;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageViewData> mMessageList = new ArrayList<>();
    private UserViewData mUser;

    private static final int MESSAGE_LEFT = 1;
    private static final int MESSAGE_RIGHT = 2;

    private int selectedPosition = -100;

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
        if (i == MESSAGE_LEFT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_left, viewGroup, false);
            return new MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_right, viewGroup, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.bindData(mMessageList.get(i));

    }

    @Override
    public int getItemViewType(int position) {
        if (mMessageList.get(position).getFrom().equals(Constants.CURRENT_UID))
            return MESSAGE_RIGHT;
        else return MESSAGE_LEFT;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class MessageViewHolder extends BaseViewHolder<MessageViewData> {

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.image_message)
        ImageView mImageMessage;

        @BindView(R.id.text_message)
        TextView mTextMessage;

        @BindView(R.id.text_seen)
        TextView mTextSeen;

        @BindView(R.id.text_time)
        TextView mTextTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(MessageViewData message) {

            GlideApp.with(itemView)
                    .load(mUser.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);
            mTextTime.setText(Utils.getTime(message.getTime()));
            mTextSeen.setText(message.isSeen() ? "Đã xem" : "Đã chuyển");

            if (message.getType().equals(MessageTypeConfig.TEXT)) {
                mTextMessage.setText(message.getMessage());
                mTextMessage.setVisibility(View.VISIBLE);
                mImageMessage.setVisibility(View.GONE);
            } else {
                mTextMessage.setVisibility(View.INVISIBLE);
                mImageMessage.setVisibility(View.VISIBLE);
                GlideApp.with(itemView)
                        .load(message.getMessage())
                        .transform(new RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL))
                        .into(mImageMessage);
            }

            mTextTime.setVisibility(View.GONE);
            mTextSeen.setVisibility(View.GONE);
        }

        @OnClick(R.id.text_message)
        void onClickMessage() {
            if (selectedPosition != -100 && selectedPosition != getAdapterPosition() || mTextSeen.getVisibility() == View.VISIBLE) {
                notifyItemChanged(selectedPosition);
            }
            if (mTextSeen.getVisibility() != View.VISIBLE){
                mTextSeen.setVisibility(View.VISIBLE);
                mTextTime.setVisibility(View.VISIBLE);
                selectedPosition = getAdapterPosition();
            }
        }
    }
}
