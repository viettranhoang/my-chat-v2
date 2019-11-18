package com.vit.mychat.cache.features.auth;

import com.vit.mychat.cache.common.PrefUtils;
import com.vit.mychat.data.auth.source.AuthCache;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthCacheImpl implements AuthCache {

    @Inject
    PrefUtils prefUtils;

    @Inject
    public AuthCacheImpl(){
    }

    @Override
    public String getCurrentUserId() {
        return prefUtils.get(PrefUtils.PREF_KEY.CURRENT_USER_ID, null);
    }

    @Override
    public void setCurrentUserId(String uid) {
        prefUtils.set(PrefUtils.PREF_KEY.CURRENT_USER_ID, uid);
    }

    @Override
    public void saveCurrentUserId(String id) {
        prefUtils.set(PrefUtils.PREF_KEY.CURRENT_USER_ID, id);
    }

    @Override
    public void signOut() {
        prefUtils.clearAllKey();
    }
}
