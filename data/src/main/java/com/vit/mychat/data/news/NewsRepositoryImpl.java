package com.vit.mychat.data.news;

import com.vit.mychat.data.news.source.NewsRemote;
import com.vit.mychat.domain.usecase.news.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRepositoryImpl implements NewsRepository {

    @Inject
    NewsRemote newsRemote;

    @Inject
    public NewsRepositoryImpl() {
    }

    @Override
    public Observable<List<String>> getNewsList() {
        return newsRemote.getNewsList();
    }
}
