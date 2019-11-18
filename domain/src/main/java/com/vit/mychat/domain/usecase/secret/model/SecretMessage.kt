package com.vit.mychat.domain.usecase.secret.model

data class SecretMessage(

        val message: String,

        val from: String,

        val seen: Boolean,

        val time: Long,

        val type: String,

        val avatar: String
)