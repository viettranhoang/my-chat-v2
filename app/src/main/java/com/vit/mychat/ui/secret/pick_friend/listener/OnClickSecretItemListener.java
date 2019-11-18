package com.vit.mychat.ui.secret.pick_friend.listener;

import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickSecretItemListener {
    void onClickSearchItem(UserViewData userViewData);

    void onClickInfo(String userId);
}
