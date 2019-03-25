package com.vit.mychat.ui.bot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseFragment;

public class BotFragment extends BaseFragment {

    public static final String TAG = BotFragment.class.getSimpleName();

    public static BotFragment newInstance() {
        BotFragment fragment = new BotFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bot_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
