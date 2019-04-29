package com.vit.mychat.domain.usecase.news.repository;

import java.util.List;

import io.reactivex.Observable;

public interface NewsRepository {

    Observable<List<String>> getNewsList();
}
