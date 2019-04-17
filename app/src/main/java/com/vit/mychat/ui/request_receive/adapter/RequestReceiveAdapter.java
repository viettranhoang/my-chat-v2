package com.vit.mychat.ui.request_receive.adapter;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;

import javax.inject.Inject;

@PerActivity
public class RequestReceiveAdapter {

    @Inject
    OnClickRequestReceiveItemListener listener;

    @Inject
    RequestReceiveAdapter() {
    }
}
