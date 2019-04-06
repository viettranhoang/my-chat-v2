package com.vit.mychat.presentation;

public interface Mapper<T, E> {

    E mapToViewData(T type);

    T mapFromViewData(E type);

}