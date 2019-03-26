package com.vit.mychat.ui.chat.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.data.model.Chat;
import com.vit.mychat.util.GlideApp;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> mChatList = new ArrayList<>();

    public ChatAdapter(List<Chat> mChatList) {

        this.mChatList = mChatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item, viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatListViewHolder, int i) {
        chatListViewHolder.bindData(mChatList.get(i));

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    class ChatViewHolder extends BaseViewHolder<Chat> {
        @BindView(R.id.image_avatar)
        ImageView mAvatar;

        @BindView(R.id.image_online)
        ImageView mImageOnline;

        @BindView(R.id.text_name)
        TextView mTextName;

        @BindView(R.id.text_message)
        TextView mTextLastMessage;

        @BindView(R.id.text_time_seen)
        TextView mTextTimeSeen;

        @BindView(R.id.image_seen)
        ImageView mImageSeen;

        @BindColor(R.color.black87)
        int mBlack87;

        public ChatViewHolder(@NonNull View itemView) {


            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        @Override
        public void bindData(Chat chatList) {
            GlideApp.with(itemView.getContext())
                    .load(chatList.getAvatar())
                    .circleCrop()
                    .into(mAvatar);

            // messlist
            mTextTimeSeen.setText(Utils.getCurrentTime());
            if (chatList.getOnline()) {
                mImageOnline.setVisibility(View.VISIBLE);
            } else {
                mImageOnline.setVisibility(View.INVISIBLE);
            }

            GlideApp.with(itemView.getContext())
                    .load(R.drawable.shape_oval_blue)
                    .centerCrop()
                    .into(mImageSeen);
            mTextLastMessage.setText(chatList.getLastMessage());
            if (chatList.getSeen() == false) {
                mImageSeen.setVisibility(View.VISIBLE);
                mTextName.setText(chatList.getName());
                mTextName.setTypeface(mTextName.getTypeface(), Typeface.DEFAULT_BOLD.getStyle());
                mTextLastMessage.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                mTextLastMessage.setTextColor(mBlack87);

            } else {
                mImageSeen.setVisibility(View.GONE);
                mTextName.setText(chatList.getName());
            }
        }
    }
}
