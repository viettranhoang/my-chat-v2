package com.vit.mychat.ui.secret

import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import com.vit.mychat.R
import com.vit.mychat.data.secret_message.source.SecretCache
import com.vit.mychat.remote.common.Constants
import com.vit.mychat.remote.feature.MyChatFirestore
import com.vit.mychat.ui.base.BaseFragment
import com.vit.mychat.util.DiffieHellman
import javax.inject.Inject


class SecretFragment : BaseFragment() {

    @Inject
    lateinit var myChatFirestore: MyChatFirestore

    @Inject
    lateinit var secretCache: SecretCache

    @Inject
    lateinit var diffieHellman: DiffieHellman

    private val database by lazy { FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_SECRET_MESSSAGE) }

    override fun getLayoutId(): Int = R.layout.bot_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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