package com.vit.mychat.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseFragment;
import com.vit.mychat.ui.chat.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatFragment extends BaseFragment {

    @BindView(R.id.list_mess)
    RecyclerView mRcvChat;

    private ChatAdapter mChatAdapter;

    @Override
    public int getLayoutId() {

        return R.layout.chat_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChatAdapter = new ChatAdapter(creatListMessage());

        initRcv();
    }

    private void initRcv() {

        mRcvChat.setLayoutManager(new LinearLayoutManager(mainActivity));
        mRcvChat.setHasFixedSize(true);
        mRcvChat.setAdapter(mChatAdapter);

    }

    private List<Chat> creatListMessage() {
        List<Chat> listChat = new ArrayList<>();
        listChat.add(new Chat("kim le", "https://gaubongonline.vn/wp-content/uploads/2018/10/qoobee-2.jpg", false, "", "hanh ham", true));
        listChat.add(new Chat("kim le", "https://gaubongonline.vn/wp-content/uploads/2018/10/qoobee-2.jpg", false, "", "hanh ham", true));
        listChat.add(new Chat("kim le", "https://gaubongonline.vn/wp-content/uploads/2018/10/qoobee-2.jpg", false, "", "hanh ham", true));
        listChat.add(new Chat("ham", "https://i.pinimg.com/236x/e5/dc/47/e5dc47a1477c10bded824a2337cdaa01--character-design.jpg", true, "", "do con nhợn ", false));
        listChat.add(new Chat("ham", "https://i.pinimg.com/236x/e5/dc/47/e5dc47a1477c10bded824a2337cdaa01--character-design.jpg", true, "", "do con nhợn ", false));
        listChat.add(new Chat("viet", "https://i.pinimg.com/474x/1d/1d/4f/1d1d4fdd1b26179130054cb5403b6242.jpg", false, "", "dfgadjapfk;dgogfvdnbakdas ", true));
        listChat.add(new Chat("viet", "https://i.pinimg.com/474x/1d/1d/4f/1d1d4fdd1b26179130054cb5403b6242.jpg", false, "", "dfgadjapfk;dgogfvdnbakdas ", true));
        listChat.add(new Chat("viet", "https://i.pinimg.com/474x/1d/1d/4f/1d1d4fdd1b26179130054cb5403b6242.jpg", false, "", "dfgadjapfk;dgogfvdnbakdas ", true));
        listChat.add(new Chat("hanh vu", "https://i.pinimg.com/474x/1d/1d/4f/1d1d4fdd1b26179130054cb5403b6242.jpg", true, "", "sfjkalafihf ", true));
        listChat.add(new Chat("hanh vu", "https://i.pinimg.com/474x/1d/1d/4f/1d1d4fdd1b26179130054cb5403b6242.jpg", true, "", "sfjkalafihf ", true));
        return listChat;
    }
}
