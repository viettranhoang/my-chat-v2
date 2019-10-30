package com.vit.mychat.util;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vit.mychat.remote.common.Constants;

public class AppLifecycleObserver implements LifecycleObserver {

    private DatabaseReference userDatabase;
    private FirebaseAuth auth;

    public AppLifecycleObserver() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference(com.vit.mychat.remote.common.Constants.TABLE_DATABASE).child(Constants.TABLE_USER);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onMoveToForeground() {
        setOnline(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onMoveToBackground() {
        setOnline(false);
    }

    public void setOnline(boolean isOnline) {
       if (!TextUtils.isEmpty(auth.getUid())) {
           userDatabase.child(auth.getUid()).child("online").setValue(isOnline ? 1 : System.currentTimeMillis());
       }
    }
}
