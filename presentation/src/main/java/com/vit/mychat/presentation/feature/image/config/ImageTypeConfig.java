package com.vit.mychat.presentation.feature.image.config;

import android.support.annotation.StringDef;

import static com.vit.mychat.presentation.feature.image.config.ImageTypeConfig.AVATAR;
import static com.vit.mychat.presentation.feature.image.config.ImageTypeConfig.COVER;
import static com.vit.mychat.presentation.feature.image.config.ImageTypeConfig.MESSAGE;
import static com.vit.mychat.presentation.feature.image.config.ImageTypeConfig.NEWS;

@StringDef({NEWS, AVATAR, COVER, MESSAGE})
public @interface ImageTypeConfig {
    String NEWS = "news";
    String AVATAR = "avatar";
    String COVER = "cover";
    String MESSAGE = "message";
}