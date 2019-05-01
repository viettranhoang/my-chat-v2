package com.vit.mychat.presentation.feature.message.config;

import android.support.annotation.StringDef;

import static com.vit.mychat.presentation.feature.message.config.MessageTypeConfig.IMAGE;
import static com.vit.mychat.presentation.feature.message.config.MessageTypeConfig.TEXT;

@StringDef({TEXT, IMAGE})
public @interface MessageTypeConfig {
    String TEXT = "text";
    String IMAGE = "image";
}