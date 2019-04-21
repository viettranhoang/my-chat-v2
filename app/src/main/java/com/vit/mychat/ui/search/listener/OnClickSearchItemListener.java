package com.vit.mychat.ui.search.listener;

import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickSearchItemListener {
    void onClickSearchItem(UserViewData userViewData);

    void onClickInfo(String userId);
}
