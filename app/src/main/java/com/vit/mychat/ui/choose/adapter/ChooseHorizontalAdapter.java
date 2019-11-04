package com.vit.mychat.ui.choose.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseHorizontalAdapter extends RecyclerView.Adapter<ChooseHorizontalAdapter.ChooseHorizontalViewHolder> {

    @Inject
    OnClickChooseHorizontalItemListener listener;

    private List<UserViewData> list = new ArrayList<>();

    @Inject
    public ChooseHorizontalAdapter() {
    }

    public void setListHorizontal(List<UserViewData> mListUserSearch) {
        this.list = mListUserSearch;
        notifyDataSetChanged();
    }

    public List<UserViewData> getList() {
        return list;
    }

    public void addItem(UserViewData userViewData) {
        list.add(userViewData);
        notifyDataSetChanged();
    }

    public void removeItem(UserViewData userViewData) {
        list.remove(userViewData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChooseHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.choose_friend_horizontal_item, viewGroup, false);
        return new ChooseHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseHorizontalViewHolder chooseHorizontalViewHolder, int i) {
        chooseHorizontalViewHolder.bindData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChooseHorizontalViewHolder extends BaseViewHolder<UserViewData> {

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.text_name)
        TextView mTextName;

        public ChooseHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(UserViewData userViewData) {
            GlideApp.with(itemView)
                    .load(userViewData.getAvatar())
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar_user)
                    .into(mImageAvatar);

            mTextName.setText(userViewData.getName());
        }

        @OnClick(R.id.image_avatar)
        void onClickChooseHorizontalItem() {
            listener.onClickChooseHorizontalItem(list.get(getLayoutPosition()));
        }


    }
}
