package com.vit.mychat.domain.usecase.news;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.news.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class GetNewsListUseCase  extends ObservableUseCase<List<String>, Void> {

    @Inject
    NewsRepository newsRepository;

    @Inject
    public GetNewsListUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                              @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<List<String>> buildUseCaseSingle(Void aVoid) {
        return newsRepository.getNewsList();
    }
}
