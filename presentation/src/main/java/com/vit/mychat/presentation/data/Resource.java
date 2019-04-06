package com.vit.mychat.presentation.data;

public class Resource<T> {

    private ResourceState status;
    private T data;
    private Throwable throwable;

    public Resource() {
    }

    public Resource(ResourceState status, T data, Throwable throwable) {
        this.status = status;
        this.data = data;
        this.throwable = throwable;
    }

    public Resource<T> success(T data) {
        return new Resource<>(ResourceState.SUCCESS, data, null);
    }

    public Resource<T> error(Throwable message) {
        return new Resource<>(ResourceState.ERROR, null, message);
    }

    public Resource<T> loading() {
        return new Resource<>(ResourceState.LOADING, null, null);
    }

    public ResourceState getStatus() {
        return status;
    }

    public void setStatus(ResourceState status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
