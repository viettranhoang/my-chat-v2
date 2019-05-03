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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.news.listener.OnClickNewsItemListener;
import com.vit.mychat.util.Constants;
import com.vit.mychat.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsAdapter extends PagerAdapter {

    @BindView(R.id.layout_avatar_online)
    RelativeLayout mLayoutAvatarOnline;

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

//    @BindView(R.id.progress_bar_news)
//    ProgressBar mProgressBarNews;

    @BindColor(R.color.gray)
    int mColorGray;

    @Inject
    OnClickNewsItemListener listener;

    private List<UserViewData> mListNews = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable;


    @Inject
    public NewsAdapter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setList(List<UserViewData> listNews) {
        listNews.add(0, Constants.CURRENT_USER);
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
        setListener(position);

        ProgressBar mProgressBarNews = itemView.findViewById(R.id.progress_bar_news);

        mCompositeDisposable.add(Observable.intervalRange(0L, 100, 1L, 60, TimeUnit.MILLISECONDS)
                .subscribe(aLoxng -> mProgressBarNews.setProgress(aLoxng.intValue())));

        mCompositeDisposable.add(Observable.interval(6000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mCompositeDisposable.add(Observable.intervalRange(0L, 100, 1L, 60, TimeUnit.MILLISECONDS)
                            .subscribe(aLoxng -> mProgressBarNews.setProgress(aLoxng.intValue())));
                }));

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mCompositeDisposable.clear();
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
        } else {
            mImageOnline.setVisibility(View.INVISIBLE);
            mTextOnline.setText(Utils.getTime(userViewData.getOnline()));
        }
    }

    private void setListener(int position) {
        UserViewData userViewData = mListNews.get(position);

        mLayoutAvatarOnline.setOnClickListener(v -> listener.onClickAvatar(userViewData));

        mImageSend.setOnClickListener(v -> {
            listener.onClickSend(userViewData.getId(), mInputMessage.getText().toString());
            mInputMessage.setText("");
        });

        mImageHeart.setOnClickListener(v -> listener.onClickHeart(userViewData.getId(),
                String.format("%s đã thích story của bạn", Constants.CURRENT_USER.getName())));
    }
}
