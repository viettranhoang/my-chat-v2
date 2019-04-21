package com.vit.mychat.ui.request_receive.listener;

import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickRequestReceiveItemListener {
    void onClickRequestReceiveItem(UserViewData user);

    void onClickAcceptRequest(UserViewData user);

    void onClickCancelRequest(UserViewData user);
}
