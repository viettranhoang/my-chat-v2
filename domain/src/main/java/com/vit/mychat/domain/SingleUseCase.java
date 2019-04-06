package com.vit.mychat.domain;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;


public abstract class SingleUseCase<T, Params> {

    private Scheduler threadExecutor;
    private Scheduler postExecutionThread;

    public SingleUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Single<T> buildUseCaseSingle(Params params);

    public void execute(SingleObserver<T> singleObserver, Params params) {
      buildUseCaseSingle(params)
                .subscribeOn(threadExecutor)
                .observeOn(postExecutionThread)
                .subscribe(singleObserver);
    }
}
