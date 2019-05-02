package com.vit.mychat.ui.choose.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseViewHolder;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;
import com.vit.mychat.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseVerticalAdapter extends RecyclerView.Adapter<ChooseVerticalAdapter.ChooseVerticalViewHolder> {


    @Inject
    OnClickChooseVerticalItemListener listener;

    private List<UserViewData> list = new ArrayList<>();

    private int selectedPosition = -100;

    @Inject
    public ChooseVerticalAdapter() {
    }


    public void setListVertical(List<UserViewData> mListUserSearch) {
        this.list = mListUserSearch;
        notifyDataSetChanged();
    }

    public List<UserViewData> getList() {
        return list;
    }
    @NonNull
    @Override
    public ChooseVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.choose_friend_vertical_item, viewGroup, false);

        return new ChooseVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseVerticalViewHolder chooseVerticalViewHolder, int i) {
        chooseVerticalViewHolder.bindData(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ChooseVerticalViewHolder extends BaseViewHolder<UserViewData> {

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.image_online)
        ImageView mImageOnline;

        @BindView(R.id.text_name)
        TextView mTextName;

        @BindView(R.id.check_choose_friend)
        CheckBox mCheckChoose;


        public ChooseVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(UserViewData userViewData) {

            mTextName.setText(userViewData.getName());
            GlideApp.with(itemView)
                    .load(userViewData.getAvatar())
                    .circleCrop()
                    .into(mImageAvatar);

            if (userViewData.getOnline() == Constants.ONLINE) {
                mImageOnline.setVisibility(View.VISIBLE);
            } else {
                mImageOnline.setVisibility(View.INVISIBLE);
            }
        }

        @OnClick(R.id.layout_root)
        void OnClickItem(){
            mCheckChoose.setChecked(!mCheckChoose.isChecked());
            listener.onClickChooseVerticalItem(list.get(getLayoutPosition()), mCheckChoose.isChecked());
        }
    }
}
