package com.vit.mychat.ui.choose.adapter;

import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChooseVerticalAdapter {

    @Inject
    OnClickChooseVerticalItemListener listener;

    private List<UserViewData> list = new ArrayList<>();

    @Inject
    public ChooseVerticalAdapter() {
    }
}
