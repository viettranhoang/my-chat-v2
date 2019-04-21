package com.vit.mychat.ui.request_receive.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class RequestReceiveAdapter extends RecyclerView.Adapter<RequestReceiveAdapter.RequestReceiveViewHolder> {

    private List<UserViewData> mListUserReceive = new ArrayList<>();


    @Inject
    OnClickRequestReceiveItemListener listener;

    @Inject
    RequestReceiveAdapter() {
    }

    public void setRequestReceive(List<UserViewData> mListUserReceive) {
        this.mListUserReceive = mListUserReceive;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RequestReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_receive_item, viewGroup, false);
        return new RequestReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestReceiveViewHolder requestReceiveViewHolder, int i) {
        requestReceiveViewHolder.bindData(mListUserReceive.get(i));

    }

    @Override
    public int getItemCount() {
        return mListUserReceive.size();
    }

    class RequestReceiveViewHolder extends BaseViewHolder<UserViewData> {

        @BindView(R.id.image_avatar_sent)
        ImageView imageAvatarSent;

        @BindView(R.id.text_name_user)
        TextView textNameUser;

        @BindView(R.id.image_cancel)
        ImageView imageCancel;

        @BindView(R.id.image_accept)
        ImageView imagaeAccept;

        @BindView(R.id.image_online)
        ImageView imageOnline;

        public RequestReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(UserViewData userViewData) {
            GlideApp.with(itemView.getContext())
                    .load(userViewData.getAvatar())
                    .circleCrop()
                    .into(imageAvatarSent);

            textNameUser.setText(userViewData.getName());

        }
    }
}
