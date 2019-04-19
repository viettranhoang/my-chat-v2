package com.vit.mychat.ui.request_sent.adapter;

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
import com.vit.mychat.ui.request_sent.listener.OnClickRequestSentItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class RequestSentAdapter  extends RecyclerView.Adapter<RequestSentAdapter.RequestSentViewHolder> {

    private List<UserViewData> mListUser =  new ArrayList<>();

    @Inject
    OnClickRequestSentItemListener listener;

    @Inject
    RequestSentAdapter() {
    }

    public void setRequestSent(List<UserViewData> mListUser){
        this.mListUser = mListUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestSentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_sent_item, viewGroup, false);
        return new RequestSentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestSentViewHolder requestSentViewHolder, int i) {
        requestSentViewHolder.bindData(mListUser.get(i));
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }


    class RequestSentViewHolder extends BaseViewHolder<UserViewData>{
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



        public RequestSentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
