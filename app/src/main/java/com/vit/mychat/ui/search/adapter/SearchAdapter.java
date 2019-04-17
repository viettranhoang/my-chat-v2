package com.vit.mychat.ui.search.adapter;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import javax.inject.Inject;

@PerActivity
public class SearchAdapter {

    @Inject
    OnClickSearchItemListener listener;

    @Inject
    SearchAdapter() {
    }
}
