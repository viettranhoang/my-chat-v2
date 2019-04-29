package com.vit.mychat.ui.news.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.news.listener.OnClickNewsItemListener;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class NewsAdapter extends PagerAdapter {

    @BindView(R.id.image_news)
    PhotoView mImageNews;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.text_name)
    TextView mTextName;

    @BindView(R.id.image_online)
    ImageView mImageOnline;

    @BindView(R.id.text_online)
    TextView mTextOnline;

    @BindView(R.id.input_message)
    EditText mInputMessage;

    @BindView(R.id.image_send)
    ImageView mImageSend;

    @BindView(R.id.image_heart)
    ImageView mImageHeart;

    @BindColor(R.color.gray)
    int mColorGray;

    @Inject
    OnClickNewsItemListener listener;

    private List<UserViewData> mListNews = new ArrayList<>();

    private int userPosition = -1;

    @Inject
    public NewsAdapter() {
    }

    public void setList(List<UserViewData> listNews) {
        this.mListNews = listNews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListNews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.news_item, container, false);
        ButterKnife.bind(this, itemView);

        binData(mListNews.get(position));
        userPosition = position;

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @OnClick(R.id.image_heart)
    void onClickHeart() {
        listener.onClickHeart();
    }

    @OnClick(R.id.image_send)
    void onClickSend() {
        listener.onClickSend(mListNews.get(userPosition).getId(), mInputMessage.getText().toString());
        mInputMessage.setText("");
    }

    @OnTextChanged(R.id.input_message)
    void onTextChangeMessage() {
        if (!TextUtils.isEmpty(mInputMessage.getText())) {
            mImageHeart.setVisibility(View.INVISIBLE);
            mImageSend.setVisibility(View.VISIBLE);
        } else {
            mImageHeart.setVisibility(View.VISIBLE);
            mImageSend.setVisibility(View.INVISIBLE);
        }
    }

    private void binData(UserViewData userViewData) {
        GlideApp.with(mImageNews.getContext())
                .load(userViewData.getNews())
                .into(mImageNews);

        GlideApp.with(mImageNews.getContext())
                .load(userViewData.getAvatar())
                .circleCrop()
                .into(mImageAvatar);
        mTextName.setText(userViewData.getName());
        mTextName.setTextColor(Color.WHITE);
        mTextOnline.setVisibility(View.VISIBLE);
        mTextOnline.setTextColor(mColorGray);

        if (userViewData.getOnline() == 1) {
            mImageOnline.setVisibility(View.VISIBLE);
            mTextOnline.setText(R.string.dang_hoat_dong);
        }
        else {
            mImageOnline.setVisibility(View.INVISIBLE);
            mTextName.setText(Utils.getTime(userViewData.getOnline()));
        }
    }


}
