package com.vit.mychat.data.auth.source;

public interface AuthCache {

    String getCurrentUserId();

    void saveCurrentUserId(String id);
}
