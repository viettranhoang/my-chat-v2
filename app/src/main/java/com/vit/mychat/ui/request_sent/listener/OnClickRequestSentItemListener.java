package com.vit.mychat.ui.request_sent.listener;

import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickRequestSentItemListener {

    void onClickRequestSentItem(UserViewData user);

    void onClickCancelRequest(UserViewData user);
}
