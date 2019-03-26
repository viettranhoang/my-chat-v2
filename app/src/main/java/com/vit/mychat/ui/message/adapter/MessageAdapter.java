package com.vit.mychat.ui.message.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.data.model.Message1;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.util.GlideApp;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message1> message1List = new ArrayList<>();

    private int selectedPosition = 100;


    public MessageAdapter(List<Message1> message1List){
        this.message1List= message1List;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messenger_item,viewGroup,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.bindData(message1List.get(i));

    }

    @Override
    public int getItemCount() {
        return message1List.size();
    }

    class MessageViewHolder extends BaseViewHolder<Message1>{

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
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindData(Message1 message1) {

            GlideApp.with(itemView.getContext())
                    .load(message1.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);

            mTextMessage.setText(message1.getContent());
            String seen = message1.getSeen() ? "Đã xem" : "";
            mTextSeen.setText(seen);
            mTextTime.setText(Utils.getCurrentTime());

            if (selectedPosition != getLayoutPosition()) {
                mTextTime.setVisibility(View.GONE);
                mTextSeen.setVisibility(View.GONE);
            }


        }
    }
}
