package com.vit.mychat.data.news.source;

import java.util.List;

import io.reactivex.Observable;

public interface NewsRemote {

    Observable<List<String>> getNewsList();

}
