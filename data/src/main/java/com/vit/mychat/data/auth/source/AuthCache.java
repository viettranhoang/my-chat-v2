package com.vit.mychat.data.auth.source;

public interface AuthCache {

    String getCurrentUserId();

    void setCurrentUserId(String uid);

    void saveCurrentUserId(String id);
}
