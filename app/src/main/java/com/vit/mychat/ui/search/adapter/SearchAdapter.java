package com.vit.mychat.ui.search.adapter;

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
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@PerActivity
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<UserViewData> mListUserSearch = new ArrayList<>();


    @Inject
    OnClickSearchItemListener listener;

    @Inject
    SearchAdapter() {
    }

    public void setList(List<UserViewData> mListUserSearch) {
        this.mListUserSearch = mListUserSearch;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.bindData(mListUserSearch.get(i));

    }

    @Override
    public int getItemCount() {
        return mListUserSearch.size();
    }

    class SearchViewHolder extends BaseViewHolder<UserViewData> {

        @BindView(R.id.image_avatar)
        ImageView mImageAvatar;

        @BindView(R.id.text_name)
        TextView mTextName;

        @BindView(R.id.image_online)
        ImageView mImageOnline;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(UserViewData userViewData) {
            GlideApp.with(itemView.getContext())
                    .load(userViewData.getAvatar())
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar_user)
                    .into(mImageAvatar);

            mTextName.setText(userViewData.getName());

            if (userViewData.getOnline() == 1)
                mImageOnline.setVisibility(View.VISIBLE);
            else
                mImageOnline.setVisibility(View.INVISIBLE);
        }

        @OnClick(R.id.layout_root)
        void onClickItem() {
            listener.onClickSearchItem(mListUserSearch.get(getAdapterPosition()));
        }

        @OnClick(R.id.image_info)
        void onClickInfo() {
            listener.onClickInfo(mListUserSearch.get(getAdapterPosition()).getId());
        }
    }
}
