package com.vit.mychat.remote.feature.news;

import com.vit.mychat.data.news.source.NewsRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRemoteImpl implements NewsRemote {

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public NewsRemoteImpl() {
    }

    @Override
    public Observable<List<String>> getNewsList() {
        return myChatFirestore.getNewsList();
    }
}
