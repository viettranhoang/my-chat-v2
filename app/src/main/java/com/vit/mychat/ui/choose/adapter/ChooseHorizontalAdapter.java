package com.vit.mychat.ui.choose.adapter;

import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChooseHorizontalAdapter {

    @Inject
    OnClickChooseHorizontalItemListener listener;

    private List<UserViewData> list = new ArrayList<>();

    @Inject
    public ChooseHorizontalAdapter() {
    }
}
