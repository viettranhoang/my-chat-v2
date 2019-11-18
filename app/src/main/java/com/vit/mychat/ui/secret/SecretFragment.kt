package com.vit.mychat.ui.secret

import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vit.mychat.R
import com.vit.mychat.data.secret_message.source.SecretMessageCache
import com.vit.mychat.remote.common.Constants
import com.vit.mychat.remote.feature.MyChatFirestore
import com.vit.mychat.ui.base.BaseFragment
import com.vit.mychat.util.DiffieHellman
import javax.inject.Inject


class SecretFragment : BaseFragment() {

    @Inject
    lateinit var myChatFirestore: MyChatFirestore

    @Inject
    lateinit var secretMessageCache: SecretMessageCache

    @Inject
    lateinit var diffieHellman: DiffieHellman

    private val database by lazy { FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_SECRET_MESSSAGE) }

    override fun getLayoutId(): Int = R.layout.bot_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database.child("idA").child("idB").child("id_message")
                .child("publicKeyB").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val publicKeyB = dataSnapshot.getValue(String::class.java)
                        diffieHellman.setReceiverPublicKey(publicKeyB!!)
                        val msg = diffieHellman.encrypt("Vit")

                        database.child("idA").child("idB").child("id_message")
                                .child("msgb").setValue(msg)

                        showToast(msg)

                        secretMessageCache.savePublicKey("idB", publicKeyB)

                        showToast(diffieHellman.decrypt(msg))
                    }
                })
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