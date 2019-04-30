package com.vit.mychat.ui.news.listener;

import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickNewsItemListener {

    void onClickSend(String userId, String message);

    void onClickHeart(String userId, String message);

    void onClickAvatar(UserViewData userViewData);
}
