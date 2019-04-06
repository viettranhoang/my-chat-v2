package com.vit.mychat.remote.common;

public interface Mapper<M, E> {
    E mapToEntity(M type);

    M mapToModel(E type);
}

