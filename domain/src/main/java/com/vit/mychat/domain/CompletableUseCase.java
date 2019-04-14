package com.vit.mychat.domain;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;

public abstract class CompletableUseCase<Params> {

    private Scheduler threadExecutor;
    private Scheduler postExecutionThread;

    public CompletableUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Completable buildUseCaseSingle(Params params);

    public void execute(CompletableObserver completableObserver, Params params) {
      buildUseCaseSingle(params)
                .subscribeOn(threadExecutor)
                .observeOn(postExecutionThread)
                .subscribe(completableObserver);
    }
}