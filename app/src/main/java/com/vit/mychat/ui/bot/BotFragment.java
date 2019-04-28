package com.vit.mychat.ui.bot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.chat.model.ChatModel;
import com.vit.mychat.ui.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BotFragment extends BaseFragment {

    public static final String TAG = BotFragment.class.getSimpleName();

    public static BotFragment newInstance() {
        BotFragment fragment = new BotFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject
    MyChatFirestore myChatFirestore;

    @Override
    public int getLayoutId() {
        return R.layout.bot_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        myChatFirestore.getChatList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatModels -> {
                    for (ChatModel chatModel : chatModels) {
                        if (chatModel.getGroup() == null) Log.i(TAG, "onViewCreated: hihi");
                    }
                });

    }
}
