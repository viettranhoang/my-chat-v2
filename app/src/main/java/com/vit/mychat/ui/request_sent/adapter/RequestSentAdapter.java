package com.vit.mychat.ui.request_sent.adapter;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.request_sent.listener.OnClickRequestSentItemListener;

import javax.inject.Inject;

@PerActivity
public class RequestSentAdapter {

    @Inject
    OnClickRequestSentItemListener listener;

    @Inject
    RequestSentAdapter() {
    }
}
