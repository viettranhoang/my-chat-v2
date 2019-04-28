package com.vit.mychat.ui.chat.listener;

import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

public interface OnClickChatItemListener {

    void onClickUserChatItem(UserViewData userViewData);

    void onClickGroupChatItem(GroupViewData groupViewData);

}
