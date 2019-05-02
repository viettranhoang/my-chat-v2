package com.vit.mychat.remote.feature.user.config;

import android.support.annotation.StringDef;

import static com.vit.mychat.remote.feature.user.config.UserRelationshipConfig.FRIEND;
import static com.vit.mychat.remote.feature.user.config.UserRelationshipConfig.NOT;
import static com.vit.mychat.remote.feature.user.config.UserRelationshipConfig.RECEIVE;
import static com.vit.mychat.remote.feature.user.config.UserRelationshipConfig.SENT;

@StringDef({FRIEND, SENT, RECEIVE, NOT})
public @interface UserRelationshipConfig {
    String FRIEND = "friend";
    String SENT = "sent";
    String RECEIVE = "receive";
    String NOT = "not";
}