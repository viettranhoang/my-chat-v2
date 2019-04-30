package com.vit.mychat.ui.chat;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.chat.GetChatListViewModel;
import com.vit.mychat.presentation.feature.chat.model.ChatViewData;
import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseFragment;
import com.vit.mychat.ui.chat.adapter.ChatAdapter;
import com.vit.mychat.ui.chat.listener.OnClickChatItemListener;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.message_group.MessageGroupActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChatFragment extends BaseFragment implements OnClickChatItemListener {

    public static final String TAG = ChatFragment.class.getSimpleName();

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.list_mess)
    RecyclerView mRcvChat;

    @Inject
    ChatAdapter mChatAdapter;

    private GetChatListViewModel getChatListViewModel;

    @Override
    public int getLayoutId() {

        return R.layout.chat_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChatListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetChatListViewModel.class);

        initRcv();

        getChatListViewModel.getChatList().observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    mChatAdapter.setChatList((List<ChatViewData>) resource.getData());
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    @Override
    public void onClickUserChatItem(UserViewData userViewData) {
        MessageActivity.moveMessageActivity(mainActivity, userViewData);
    }

    @Override
    public void onClickGroupChatItem(GroupViewData groupViewData) {
        MessageGroupActivity.moveMessageActivity(mainActivity, groupViewData);
    }

    private void initRcv() {
        mRcvChat.setLayoutManager(new LinearLayoutManager(mainActivity));
        mRcvChat.setHasFixedSize(true);
        mRcvChat.setItemAnimator(new DefaultItemAnimator());
        mRcvChat.setAdapter(mChatAdapter);

    }


}
