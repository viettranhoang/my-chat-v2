package com.vit.mychat.ui.secret

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.vit.mychat.R
import com.vit.mychat.presentation.data.ResourceState
import com.vit.mychat.presentation.feature.chat.GetSecretChatListViewModel
import com.vit.mychat.presentation.feature.chat.model.ChatViewData
import com.vit.mychat.presentation.feature.group.model.GroupViewData
import com.vit.mychat.presentation.feature.user.model.UserViewData
import com.vit.mychat.ui.base.BaseFragment
import com.vit.mychat.ui.chat.adapter.ChatAdapter
import com.vit.mychat.ui.chat.listener.OnClickChatItemListener
import com.vit.mychat.ui.message_secret.MessageSecretActivity
import com.vit.mychat.util.DiffieHellman
import kotlinx.android.synthetic.main.bot_fragment.*
import javax.inject.Inject


class SecretFragment : BaseFragment(), OnClickChatItemListener {

    @Inject
    lateinit var mChatAdapter: ChatAdapter

    @Inject
    lateinit var diffieHellman: DiffieHellman

    private lateinit var getChatListViewModel: GetSecretChatListViewModel

    override fun getLayoutId(): Int = R.layout.bot_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getChatListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetSecretChatListViewModel::class.java)

        initRcv()
        diffieHellman.init()


        getChatListViewModel.chatList.observe(this, Observer{ resource ->
            when (resource!!.status) {
                ResourceState.SUCCESS -> mChatAdapter.setChatList(resource.data as List<ChatViewData>)
                ResourceState.ERROR -> Log.e("SecretM", "onViewCreated: ", resource.throwable)

            }
        })
    }

    override fun onClickUserChatItem(userViewData: UserViewData?) {
        MessageSecretActivity.moveMessageSecretActivity(mainActivity, userViewData)
    }

    override fun onClickGroupChatItem(groupViewData: GroupViewData?) {

    }

    private fun initRcv() {
        listMessage.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mChatAdapter
        }
    }

    companion object {

        val TAG = SecretFragment::class.java.simpleName.toString()

        fun newInstance(): SecretFragment {
            val fragment = SecretFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}