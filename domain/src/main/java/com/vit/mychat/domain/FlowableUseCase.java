package com.vit.mychat.domain;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;


public abstract class FlowableUseCase<T> {

    private Scheduler threadExecutor;
    private Scheduler postExecutionThread;

    public FlowableUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }


    protected abstract Flowable<T> buildUseCaseObservable();

    public Disposable execute(DisposableSubscriber<T> disposableSubscriber) {
        return buildUseCaseObservable()
                .subscribeOn(threadExecutor)
                .observeOn(postExecutionThread)
                .subscribeWith(disposableSubscriber);
    }
}
