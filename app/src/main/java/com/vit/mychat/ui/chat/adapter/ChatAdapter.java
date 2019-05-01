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
import com.vit.mychat.di.scope.PerFragment;
import com.vit.mychat.presentation.feature.chat.model.ChatViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.chat.listener.OnClickChatItemListener;
import com.vit.mychat.util.Constants;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@PerFragment
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatViewData> mChatList = new ArrayList<>();

    @Inject
    OnClickChatItemListener listener;

    @Inject
    public ChatAdapter() {
    }

    public void setChatList(List<ChatViewData> chatList) {
        this.mChatList = chatList;
        notifyDataSetChanged();
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

    class ChatViewHolder extends BaseViewHolder<ChatViewData> {
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

        boolean isUser = true;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(ChatViewData chatViewData) {

            if (chatViewData.getUser() == null)
                isUser = false;
            else if (chatViewData.getGroup() == null)
                isUser = true;
            else return;

            String avatar = isUser ? chatViewData.getUser().getAvatar() : chatViewData.getGroup().getAvatar();
            String name = isUser ? chatViewData.getUser().getName() : chatViewData.getGroup().getName();


            GlideApp.with(itemView)
                    .load(avatar)
                    .circleCrop()
                    .into(mAvatar);

            GlideApp.with(itemView)
                    .load(R.drawable.shape_oval_blue)
                    .centerCrop()
                    .into(mImageSeen);

            mTextName.setText(name);
            mTextLastMessage.setText(chatViewData.getLastMessage().getMessage());
            mTextTimeSeen.setText(Utils.getTime(chatViewData.getLastMessage().getTime()));
            mImageOnline.setVisibility(View.INVISIBLE);

            if (isUser && chatViewData.getUser().getOnline() == Constants.ONLINE) {
                mImageOnline.setVisibility(View.VISIBLE);
            }

            if (!chatViewData.getLastMessage().isSeen() &&
                    !chatViewData.getLastMessage().getFrom().equals(Constants.CURRENT_UID)) {
                mImageSeen.setVisibility(View.VISIBLE);

                mTextName.setTypeface(mTextName.getTypeface(), Typeface.DEFAULT_BOLD.getStyle());
                mTextLastMessage.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                mTextLastMessage.setTextColor(mBlack87);
            } else {
                mImageSeen.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.layout_root)
        void onClickItem() {
            if (isUser)
                listener.onClickUserChatItem(mChatList.get(getAdapterPosition()).getUser());
            else
                listener.onClickGroupChatItem(mChatList.get(getAdapterPosition()).getGroup());
        }
    }
}
