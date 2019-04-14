package com.vit.mychat.domain;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;

public abstract class ObservableUseCase<T, Params> {

    private Scheduler threadExecutor;
    private Scheduler postExecutionThread;

    public ObservableUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Observable<T> buildUseCaseSingle(Params params);

    public void execute(Observer<T> observer, Params params) {
        buildUseCaseSingle(params)
                .subscribeOn(threadExecutor)
                .observeOn(postExecutionThread)
                .subscribe(observer);
    }
}