package com.vit.mychat.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseFragment;

import butterknife.BindView;

public class ChatFragment extends BaseFragment {
 

    @Override
    public int getLayoutId() {
        return R.layout.chat_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
