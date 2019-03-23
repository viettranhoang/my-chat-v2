package com.vit.mychat.ui.chat.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.chat.Chat;
import com.vit.mychat.util.GlideApp;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> mChatList = new ArrayList<>();


    public ChatAdapter(List<Chat> mChatList){
        this.mChatList= mChatList;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item,viewGroup,false);
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
            if(chatList.getOnline()){
                mImageOnline.setVisibility(View.VISIBLE);
            }
            else{
                mImageOnline.setVisibility(View.INVISIBLE);
            }

            Glide.with(itemView.getContext())
                    .load(R.drawable.ic_online_seen)
                    .centerCrop()
                    .into(mImageSeen);
            if(chatList.getSeen()==  false){
                mImageSeen.setVisibility(View.VISIBLE);
                mTextName.setText(chatList.getName());
                mTextName.setTypeface(mTextName.getTypeface(), Typeface.BOLD);
                mTextLastMessage.setText(chatList.getLastMessage());
                mTextLastMessage.setTypeface(mTextLastMessage.getTypeface(), Typeface.BOLD);

            }

            else{
                mImageSeen.setVisibility(View.GONE);
                mTextName.setText(chatList.getName());
                mTextLastMessage.setText(chatList.getLastMessage());


            }
        }
    }
}
